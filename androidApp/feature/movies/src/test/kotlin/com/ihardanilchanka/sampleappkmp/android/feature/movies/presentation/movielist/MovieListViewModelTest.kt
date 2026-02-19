package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.movielist

import app.cash.turbine.test
import com.ihardanilchanka.sampleappkmp.android.feature.movies.fakeMovie
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.util.MainDispatcherRule
import com.ihardanilchanka.sampleappkmp.domain.usecase.MovieUseCase
import com.ihardanilchanka.sampleappkmp.domain.usecase.SelectedMovieUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val movieList = listOf(fakeMovie(1), fakeMovie(2))

    private val selectMovie: SelectedMovieUseCase.Select = mockk(relaxed = true)
    private val refreshPopularMovieList: MovieUseCase.RefreshPopular = mockk()
    private val fetchMorePopularMovies: MovieUseCase.FetchMorePopular = mockk()
    private val loadPopularMovies: MovieUseCase.LoadPopular = mockk()

    private fun createViewModel() = MovieListViewModel(
        selectMovie = selectMovie,
        refreshPopularMovieList = refreshPopularMovieList,
        fetchMorePopularMovies = fetchMorePopularMovies,
        loadPopularMovies = loadPopularMovies,
    )

    @Test
    fun `initial load shows movies from cache when cache has data`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            coEvery { loadPopularMovies() } returns movieList

            val viewModel = createViewModel()

            assertEquals(movieList, viewModel.movieListUiState.value.movies)
            assertIs<LoadingState.Ready>(viewModel.movieListUiState.value.loadingState)
        }

    @Test
    fun `initial load falls back to network fetch when cache is empty`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            val freshMovies = listOf(fakeMovie(10))
            coEvery { loadPopularMovies() } returns emptyList()
            coEvery { fetchMorePopularMovies() } returns freshMovies

            val viewModel = createViewModel()

            assertEquals(freshMovies, viewModel.movieListUiState.value.movies)
            assertIs<LoadingState.Ready>(viewModel.movieListUiState.value.loadingState)
            coVerify(exactly = 1) { fetchMorePopularMovies() }
        }

    @Test
    fun `initial load shows error state when load throws`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            coEvery { loadPopularMovies() } throws RuntimeException("Network error")

            val viewModel = createViewModel()

            assertIs<LoadingState.Error>(viewModel.movieListUiState.value.loadingState)
        }

    @Test
    fun `swipe to refresh replaces movie list on success`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            val refreshedMovies = listOf(fakeMovie(3), fakeMovie(4))
            coEvery { loadPopularMovies() } returns movieList
            coEvery { refreshPopularMovieList() } returns refreshedMovies

            val viewModel = createViewModel()
            viewModel.onSwipeToRefresh()

            assertEquals(refreshedMovies, viewModel.movieListUiState.value.movies)
            assertFalse(viewModel.movieListUiState.value.isRefreshing)
        }

    @Test
    fun `swipe to refresh shows error state on failure`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            coEvery { loadPopularMovies() } returns movieList
            coEvery { refreshPopularMovieList() } throws RuntimeException("Server error")

            val viewModel = createViewModel()
            viewModel.onSwipeToRefresh()

            assertIs<LoadingState.Error>(viewModel.movieListUiState.value.loadingState)
        }

    /* ---------- loading state transitions tests ---------- */

    @Test
    fun `initial load emits Loading then Ready`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            coEvery { loadPopularMovies() } coAnswers {
                delay(100)
                movieList
            }

            val viewModel = createViewModel()

            viewModel.movieListUiState.test {
                assertIs<LoadingState.Loading>(awaitItem().loadingState)

                val ready = awaitItem()
                assertIs<LoadingState.Ready>(ready.loadingState)
                assertEquals(movieList, ready.movies)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `initial load emits Loading then Error on failure`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            coEvery { loadPopularMovies() } coAnswers {
                delay(100)
                throw RuntimeException("Network error")
            }

            val viewModel = createViewModel()

            viewModel.movieListUiState.test {
                assertIs<LoadingState.Loading>(awaitItem().loadingState)
                assertIs<LoadingState.Error>(awaitItem().loadingState)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `swipe to refresh emits isRefreshing true before completing`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            val refreshedMovies = listOf(fakeMovie(3))
            coEvery { loadPopularMovies() } returns movieList
            coEvery { refreshPopularMovieList() } coAnswers {
                delay(100)
                refreshedMovies
            }

            val viewModel = createViewModel()

            viewModel.movieListUiState.test {
                awaitItem() // post-init Ready state

                viewModel.onSwipeToRefresh()

                assertTrue(awaitItem().isRefreshing)
                val done = awaitItem()
                assertEquals(refreshedMovies, done.movies)
                assertFalse(done.isRefreshing)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `swipe to refresh emits isRefreshing true then Error on failure`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            coEvery { loadPopularMovies() } returns movieList
            coEvery { refreshPopularMovieList() } coAnswers {
                delay(100)
                throw RuntimeException("Server error")
            }

            val viewModel = createViewModel()

            viewModel.movieListUiState.test {
                awaitItem() // post-init Ready state

                viewModel.onSwipeToRefresh()

                assertTrue(awaitItem().isRefreshing)

                val error = awaitItem()
                assertIs<LoadingState.Error>(error.loadingState)
                assertFalse(error.isRefreshing)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `onNeedLoadMore emits additionalLoadingState Loading then Ready when movies already loaded`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            val allMovies = movieList + listOf(fakeMovie(10))
            coEvery { loadPopularMovies() } returns movieList
            coEvery { fetchMorePopularMovies() } coAnswers {
                delay(100)
                allMovies
            }

            val viewModel = createViewModel()

            viewModel.movieListUiState.test {
                awaitItem() // post-init Ready state

                viewModel.onNeedLoadMore()

                val loading = awaitItem()
                assertIs<LoadingState.Loading>(loading.additionalLoadingState)
                assertIs<LoadingState.Ready>(loading.loadingState) // main loadingState unaffected

                val ready = awaitItem()
                assertIs<LoadingState.Ready>(ready.additionalLoadingState)
                assertEquals(allMovies, ready.movies)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `onNeedLoadMore emits additionalLoadingState Loading then Error on failure`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            coEvery { loadPopularMovies() } returns movieList
            coEvery { fetchMorePopularMovies() } coAnswers {
                delay(100)
                throw RuntimeException("Network error")
            }

            val viewModel = createViewModel()

            viewModel.movieListUiState.test {
                awaitItem() // post-init Ready state

                viewModel.onNeedLoadMore()

                val loading = awaitItem()
                assertIs<LoadingState.Loading>(loading.additionalLoadingState)

                val error = awaitItem()
                assertIs<LoadingState.Error>(error.additionalLoadingState)
                assertEquals(movieList, error.movies) // existing movies are preserved on error

                cancelAndIgnoreRemainingEvents()
            }
        }
}
