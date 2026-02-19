package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.movielist.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleappkmp.android.core.ui.component.CoilImage
import com.ihardanilchanka.sampleappkmp.android.core.ui.component.DividerDark
import com.ihardanilchanka.sampleappkmp.android.core.ui.component.RatingBar
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.previewMovie
import com.ihardanilchanka.sampleappkmp.android.core.ui.resource.AppTheme
import com.ihardanilchanka.sampleappkmp.domain.model.Movie

@Composable
fun MovieItem(
    index: Int,
    movie: Movie,
    onMovieItemClicked: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = { onMovieItemClicked(movie) }
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = index.toString(),
                style = MaterialTheme.typography.titleSmall,
            )
            Spacer(modifier = Modifier.size(16.dp))

            CoilImage(
                modifier = Modifier
                    .height(96.dp)
                    .aspectRatio(2 / 3f),
                imageUrl = movie.posterUrl,
            )
            Spacer(modifier = Modifier.size(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "${movie.title} (${movie.releaseDate?.year ?: "TBA"})",
                    style = MaterialTheme.typography.titleSmall,
                )
                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = movie.genreNames.joinToString(prefix = "(", postfix = ")"),
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.size(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RatingBar(
                        modifier = Modifier.height(12.dp),
                        rating = movie.voteAverage.toFloat() / 2,
                    )
                    Spacer(modifier = Modifier.size(16.dp))

                    Text(
                        text = movie.voteAverage.toString(),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
        DividerDark(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    AppTheme {
        MovieItem(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            index = 1,
            movie = previewMovie,
            onMovieItemClicked = {},
        )
    }
}
