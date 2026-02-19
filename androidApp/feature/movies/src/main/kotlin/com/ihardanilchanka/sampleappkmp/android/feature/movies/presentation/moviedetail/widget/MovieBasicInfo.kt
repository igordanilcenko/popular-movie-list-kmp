package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.moviedetail.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleappkmp.android.core.ui.component.RatingBar
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.previewMovie
import com.ihardanilchanka.sampleappkmp.android.core.ui.resource.AppTheme
import com.ihardanilchanka.sampleappkmp.domain.model.Movie

@Composable
fun MovieBasicInfo(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shadowElevation = 2.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = movie.releaseDate?.year?.toString() ?: "TBA",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = movie.genreNames.joinToString(),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.size(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RatingBar(
                    modifier = Modifier.height(36.dp),
                    rating = movie.voteAverage.toFloat() / 2,
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = movie.voteAverage.toString(),
                    style = MaterialTheme.typography.displaySmall,
                )
            }
            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieBasicInfoPreview() {
    AppTheme {
        MovieBasicInfo(modifier = Modifier.padding(bottom = 16.dp), movie = previewMovie)
    }
}
