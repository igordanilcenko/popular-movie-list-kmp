package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.moviedetail.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleappkmp.android.feature.movies.R
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.previewMovies
import com.ihardanilchanka.sampleappkmp.android.core.ui.resource.AppTheme
import com.ihardanilchanka.sampleappkmp.domain.model.Movie

@Composable
fun SimilarMovies(
    similarMovies: List<Movie>,
    onMovieSelected: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            text = stringResource(R.string.movie_detail_similar_movies_title),
            style = MaterialTheme.typography.labelSmall,
        )
        LazyRow {
            item { Spacer(modifier = Modifier.size(8.dp)) }
            items(similarMovies) { movie ->
                SimilarMovieItem(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    movie = movie,
                    onMovieItemClicked = { onMovieSelected(movie) },
                )
            }
            item { Spacer(modifier = Modifier.size(8.dp)) }
        }
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun SimilarMoviesPreview() {
    AppTheme {
        SimilarMovies(
            modifier = Modifier.padding(vertical = 16.dp),
            similarMovies = previewMovies,
            onMovieSelected = {},
        )
    }
}
