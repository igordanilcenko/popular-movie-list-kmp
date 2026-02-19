package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.movielist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.ihardanilchanka.sampleappkmp.android.core.ui.resource.AppTheme
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState
import com.ihardanilchanka.sampleappkmp.domain.model.Movie
import kotlinx.datetime.LocalDate
import org.junit.Rule
import org.junit.Test

class MovieListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val movie = Movie(
        id = 1,
        title = "The Dark Knight",
        overview = "Overview",
        releaseDate = LocalDate(2008, 7, 18),
        voteAverage = 8.5,
        posterUrl = null,
        backdropUrl = null,
        genreNames = listOf("Action"),
    )

    @Test
    fun movieListScreen_loadingState_showsLoadingIndicator() {
        composeTestRule.setContent {
            AppTheme {
                MovieListScreenContent(
                    uiState = MovieListUiState(loadingState = LoadingState.Loading),
                    onMovieItemClicked = {},
                    onBottomReached = {},
                    onReloadClicked = {},
                    onRefresh = {},
                )
            }
        }

        composeTestRule.onNodeWithTag("movie_list_loading").assertIsDisplayed()
    }

    @Test
    fun movieListScreen_errorState_showsErrorComponent() {
        composeTestRule.setContent {
            AppTheme {
                MovieListScreenContent(
                    uiState = MovieListUiState(
                        loadingState = LoadingState.Error(RuntimeException("Network error")),
                    ),
                    onMovieItemClicked = {},
                    onBottomReached = {},
                    onReloadClicked = {},
                    onRefresh = {},
                )
            }
        }

        composeTestRule.onNodeWithTag("movie_list_error").assertIsDisplayed()
    }

    @Test
    fun movieListScreen_readyState_showsMovieList() {
        composeTestRule.setContent {
            AppTheme {
                MovieListScreenContent(
                    uiState = MovieListUiState(
                        movies = listOf(movie),
                        loadingState = LoadingState.Ready,
                    ),
                    onMovieItemClicked = {},
                    onBottomReached = {},
                    onReloadClicked = {},
                    onRefresh = {},
                )
            }
        }

        composeTestRule.onNodeWithTag("movie_list_content").assertIsDisplayed()
        composeTestRule.onNodeWithText("The Dark Knight (2008)").assertIsDisplayed()
    }
}
