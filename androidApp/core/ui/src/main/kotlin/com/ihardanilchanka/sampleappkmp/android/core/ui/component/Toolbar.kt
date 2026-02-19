@file:OptIn(ExperimentalMaterial3Api::class)

package com.ihardanilchanka.sampleappkmp.android.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Toolbar(
    titleResId: Int?,
    modifier: Modifier = Modifier,
    navigateUp: (() -> Unit)? = null,
) {
    Toolbar(titleResId?.let { stringResource(it) }, modifier, navigateUp)
}

@Composable
fun Toolbar(
    title: String?,
    modifier: Modifier = Modifier,
    navigateUp: (() -> Unit)? = null,
) {
    Box(modifier.shadow(4.dp)) {
        TopAppBar(
            title = { title?.let { Text(modifier = Modifier.testTag("toolbar_title"), text = title) } },
            navigationIcon = { NavigationButton(navigateUp = navigateUp) },
        )
    }
}

@Composable
private fun NavigationButton(
    modifier: Modifier = Modifier,
    navigateUp: (() -> Unit)? = null,
) {
    if (navigateUp != null) {
        IconButton(
            modifier = modifier,
            onClick = { navigateUp() },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Arrow back",
            )
        }
    }
}

@Preview
@Composable
private fun ToolbarTitleOnlyPreview() {
    Toolbar(title = "Movie List")
}

@Preview
@Composable
private fun ToolbarWithBackButtonPreview() {
    Toolbar(title = "Movie Details", navigateUp = {})
}
