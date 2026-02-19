package com.ihardanilchanka.sampleappkmp.android.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleappkmp.android.core.ui.resource.AppColor

@Composable
fun GradientDark(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .then(modifier)
            .background(
                Brush.verticalGradient(
                    listOf(Color.Transparent, AppColor.blackTransparent75),
                )
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun GradientDarkPreview() {
    GradientDark(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    )
}
