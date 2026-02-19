package com.ihardanilchanka.sampleappkmp.android.core.ui.component

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleappkmp.android.core.ui.R
import java.io.IOException

@Composable
fun BasicError(
    modifier: Modifier = Modifier,
    error: Throwable,
    onReloadClicked: () -> Unit,
) {
    BasicError(
        modifier = modifier,
        title = error.getErrorDialogTitle(LocalContext.current),
        message = error.getErrorDialogMessage(LocalContext.current),
        onReloadClicked = onReloadClicked
    )
}

@Composable
fun BasicError(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    onReloadClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = onReloadClicked) {
            Text(stringResource(R.string.state_view_reload))
        }
    }
}

private fun Throwable.getErrorDialogTitle(context: Context): String {
    return when (this) {
        is IOException -> context.getString(R.string.state_view_error_no_internet_title)
        else -> context.getString(R.string.state_view_error_default_title)
    }
}

private fun Throwable.getErrorDialogMessage(context: Context): String {
    return when (this) {
        is IOException -> context.getString(R.string.state_view_error_no_internet_message)
        else -> context.getString(R.string.state_view_error_default_message)
    }
}

@Preview(showBackground = true)
@Composable
private fun BasicErrorPreview() {
    BasicError(
        modifier = Modifier.padding(16.dp),
        title = "Something went wrong",
        message = "Please try again later",
        onReloadClicked = {},
    )
}
