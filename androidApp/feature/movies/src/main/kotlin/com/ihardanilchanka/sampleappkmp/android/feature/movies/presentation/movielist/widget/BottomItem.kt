package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.movielist.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleappkmp.android.feature.movies.R
import com.ihardanilchanka.sampleappkmp.android.core.ui.component.BasicError
import com.ihardanilchanka.sampleappkmp.android.core.ui.component.BasicLoading
import com.ihardanilchanka.sampleappkmp.android.core.ui.resource.AppTheme
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState.Error
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState.Loading
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState.Ready

@Composable
fun BottomItem(
    loadingState: LoadingState,
    onReloadClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        when (loadingState) {
            is Ready -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.movie_list_reach_the_end),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            is Loading -> {
                BasicLoading(Modifier.fillMaxSize())
            }
            is Error -> {
                BasicError(
                    modifier = Modifier.fillMaxSize(),
                    error = loadingState.error,
                    onReloadClicked = onReloadClicked,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomItemReadyPreview() {
    AppTheme {
        BottomItem(
            loadingState = Ready,
            onReloadClicked = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomItemLoadingPreview() {
    AppTheme {
        BottomItem(
            loadingState = Loading,
            onReloadClicked = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomItemErrorPreview() {
    AppTheme {
        BottomItem(
            loadingState = Error(RuntimeException("Network error")),
            onReloadClicked = {},
        )
    }
}
