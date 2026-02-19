package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.moviedetail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleappkmp.android.feature.movies.R
import com.ihardanilchanka.sampleappkmp.android.core.ui.component.BasicError
import com.ihardanilchanka.sampleappkmp.android.core.ui.component.BasicLoading
import com.ihardanilchanka.sampleappkmp.android.core.ui.component.Toolbar
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.moviedetail.widget.MovieBasicInfo
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.moviedetail.widget.MovieHeader
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.moviedetail.widget.ReviewItem
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.moviedetail.widget.SimilarMovies
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.previewMovie
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.previewMovies
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.previewReviewLong
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.previewReviewShort
import com.ihardanilchanka.sampleappkmp.android.core.ui.resource.AppTheme
import com.ihardanilchanka.sampleappkmp.domain.model.Movie
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState.Error
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState.Loading
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState.Ready
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieDetailScreen(viewModel: MovieDetailViewModel = koinViewModel()) {
    val uiState by viewModel.movieDetailUiState.collectAsState()

    MovieDetailScreenContent(
        uiState = uiState,
        onMovieSelected = { viewModel.onMovieSelected(it) },
        onNavigateUp = { viewModel.navigateUp() },
        onReloadSimilarMovies = { viewModel.onReloadSimilarMoviesClicked() },
        onReloadReviews = { viewModel.onReloadReviewsClicked() },
    )
}

@Composable
internal fun MovieDetailScreenContent(
    uiState: MovieDetailUiState,
    onMovieSelected: (Movie) -> Unit,
    onNavigateUp: () -> Unit,
    onReloadSimilarMovies: () -> Unit,
    onReloadReviews: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Toolbar(
                title = uiState.movie?.title,
                navigateUp = onNavigateUp,
            )
        }
    ) { innerPadding ->
        MovieDetailContent(
            modifier = Modifier.padding(innerPadding),
            movie = uiState.movie,
            similarMoviesUiState = uiState.similarMoviesUiState,
            reviewsUiState = uiState.reviewsUiState,
            onMovieSelected = onMovieSelected,
            onReloadSimilarMovies = onReloadSimilarMovies,
            onReloadReviews = onReloadReviews,
        )
    }
}

@Composable
private fun MovieDetailContent(
    movie: Movie?,
    similarMoviesUiState: SimilarMoviesUiState,
    reviewsUiState: ReviewsUiState,
    onMovieSelected: (Movie) -> Unit,
    onReloadSimilarMovies: () -> Unit,
    onReloadReviews: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        movie?.let { movieData ->
            item { MovieHeader(movie = movieData) }
            item { MovieBasicInfo(modifier = Modifier.padding(bottom = 16.dp), movie = movieData) }
        }

        when (similarMoviesUiState.loadingState) {
            is Ready ->
                similarMoviesUiState.similarMovies.takeIf { !it.isNullOrEmpty() }?.let { movies ->
                    item {
                        SimilarMovies(
                            similarMovies = movies,
                            onMovieSelected = onMovieSelected,
                        )
                    }
                }
            is Loading -> item {
                BasicLoading(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .testTag("similar_movies_loading")
                )
            }
            is Error -> item {
                BasicError(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .testTag("similar_movies_error"),
                    error = similarMoviesUiState.loadingState.error,
                    onReloadClicked = onReloadSimilarMovies,
                )
            }
        }

        when (reviewsUiState.loadingState) {
            is Ready ->
                reviewsUiState.reviews.takeIf { !it.isNullOrEmpty() }?.let { reviews ->
                    item {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 16.dp),
                            text = stringResource(R.string.movie_detail_review_title),
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                    items(reviews) { review ->
                        ReviewItem(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth(),
                            review = review,
                        )
                    }
                    item { Spacer(modifier = Modifier.size(8.dp)) }
                }
            is Loading -> item {
                BasicLoading(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .testTag("reviews_loading")
                )
            }
            is Error -> item {
                BasicError(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .testTag("reviews_error"),
                    error = reviewsUiState.loadingState.error,
                    onReloadClicked = onReloadReviews,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailScreenSuccessPreview() {
    AppTheme {
        MovieDetailScreenContent(
            uiState = MovieDetailUiState(
                movie = previewMovie,
                similarMoviesUiState = SimilarMoviesUiState(
                    similarMovies = previewMovies,
                    loadingState = Ready,
                ),
                reviewsUiState = ReviewsUiState(
                    reviews = listOf(previewReviewShort, previewReviewLong),
                    loadingState = Ready,
                ),
            ),
            onMovieSelected = {},
            onNavigateUp = {},
            onReloadSimilarMovies = {},
            onReloadReviews = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailScreenLoadingPreview() {
    AppTheme {
        MovieDetailScreenContent(
            uiState = MovieDetailUiState(
                movie = previewMovie,
                similarMoviesUiState = SimilarMoviesUiState(loadingState = Loading),
                reviewsUiState = ReviewsUiState(loadingState = Loading),
            ),
            onMovieSelected = {},
            onNavigateUp = {},
            onReloadSimilarMovies = {},
            onReloadReviews = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailScreenErrorPreview() {
    AppTheme {
        MovieDetailScreenContent(
            uiState = MovieDetailUiState(
                movie = previewMovie,
                similarMoviesUiState = SimilarMoviesUiState(
                    loadingState = Error(RuntimeException("Network error")),
                ),
                reviewsUiState = ReviewsUiState(
                    loadingState = Error(RuntimeException("Network error")),
                ),
            ),
            onMovieSelected = {},
            onNavigateUp = {},
            onReloadSimilarMovies = {},
            onReloadReviews = {},
        )
    }
}
