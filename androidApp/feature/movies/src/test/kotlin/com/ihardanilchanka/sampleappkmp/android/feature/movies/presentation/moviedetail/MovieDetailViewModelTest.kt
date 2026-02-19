package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.moviedetail

import com.ihardanilchanka.sampleappkmp.android.feature.movies.fakeMovie
import com.ihardanilchanka.sampleappkmp.android.feature.movies.fakeReview
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.util.MainDispatcherRule
import com.ihardanilchanka.sampleappkmp.domain.usecase.LoadReviewListUseCase
import com.ihardanilchanka.sampleappkmp.domain.usecase.MovieUseCase
import com.ihardanilchanka.sampleappkmp.domain.usecase.NavigateUpUseCase
import com.ihardanilchanka.sampleappkmp.domain.usecase.SelectedMovieUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val selectedMovie = fakeMovie(id = 42)

    private val getSelectedMovie: SelectedMovieUseCase.Load = mockk()
    private val loadSimilarMovies: MovieUseCase.LoadSimilar = mockk()
    private val loadReviews: LoadReviewListUseCase = mockk()
    private val goBack: NavigateUpUseCase = mockk(relaxed = true)
    private val selectMovie: SelectedMovieUseCase.Select = mockk(relaxed = true)

    private fun createViewModel(): MovieDetailViewModel {
        every { getSelectedMovie() } returns selectedMovie
        return MovieDetailViewModel(
            getSelectedMovie = getSelectedMovie,
            loadSimilarMovies = loadSimilarMovies,
            loadReviews = loadReviews,
            goBack = goBack,
            selectMovie = selectMovie,
        )
    }

    @Test
    fun `initial state contains the selected movie`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            coEvery { loadSimilarMovies(selectedMovie) } returns emptyList()
            coEvery { loadReviews(selectedMovie) } returns emptyList()

            val viewModel = createViewModel()

            assertEquals(selectedMovie, viewModel.movieDetailUiState.value.movie)
        }

    @Test
    fun `init loads similar movies on success`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            val similarMovies = listOf(fakeMovie(2), fakeMovie(3))
            coEvery { loadSimilarMovies(selectedMovie) } returns similarMovies
            coEvery { loadReviews(selectedMovie) } returns emptyList()

            val viewModel = createViewModel()

            assertEquals(
                similarMovies,
                viewModel.movieDetailUiState.value.similarMoviesUiState.similarMovies
            )
            assertEquals(
                LoadingState.Ready,
                viewModel.movieDetailUiState.value.similarMoviesUiState.loadingState
            )
        }

    @Test
    fun `init loads reviews on success`() = runTest(mainDispatcherRule.testDispatcher.scheduler) {
        val reviews = listOf(fakeReview("r1"), fakeReview("r2"))
        coEvery { loadSimilarMovies(selectedMovie) } returns emptyList()
        coEvery { loadReviews(selectedMovie) } returns reviews

        val viewModel = createViewModel()

        assertEquals(reviews, viewModel.movieDetailUiState.value.reviewsUiState.reviews)
        assertEquals(
            LoadingState.Ready,
            viewModel.movieDetailUiState.value.reviewsUiState.loadingState
        )
    }

    @Test
    fun `init shows error in similar movies when load throws`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            coEvery { loadSimilarMovies(selectedMovie) } throws RuntimeException("API error")
            coEvery { loadReviews(selectedMovie) } returns emptyList()

            val viewModel = createViewModel()

            assertIs<LoadingState.Error>(viewModel.movieDetailUiState.value.similarMoviesUiState.loadingState)
        }

    @Test
    fun `init shows error in reviews when loadReviews throws`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            coEvery { loadSimilarMovies(selectedMovie) } returns emptyList()
            coEvery { loadReviews(selectedMovie) } throws RuntimeException("API error")

            val viewModel = createViewModel()

            assertIs<LoadingState.Error>(viewModel.movieDetailUiState.value.reviewsUiState.loadingState)
            assertEquals(
                LoadingState.Ready,
                viewModel.movieDetailUiState.value.similarMoviesUiState.loadingState
            )
        }

    @Test
    fun `onReloadSimilarMoviesClicked updates similar movies`() =
        runTest(mainDispatcherRule.testDispatcher.scheduler) {
            val reloadedMovies = listOf(fakeMovie(3), fakeMovie(4))
            coEvery { loadSimilarMovies(selectedMovie) } returns emptyList()
            coEvery { loadReviews(selectedMovie) } returns emptyList()

            val viewModel = createViewModel()

            coEvery { loadSimilarMovies(selectedMovie) } returns reloadedMovies
            viewModel.onReloadSimilarMoviesClicked()

            assertEquals(
                reloadedMovies,
                viewModel.movieDetailUiState.value.similarMoviesUiState.similarMovies
            )
        }
}
