package com.ihardanilchanka.sampleappkmp.android.core.ui.component

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleappkmp.android.core.ui.resource.AppColor

@Composable
fun DividerDark(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        color = AppColor.lightGrey200,
        thickness = 1.dp,
    )
}

@Preview(showBackground = true)
@Composable
private fun DividerDarkPreview() {
    DividerDark()
}
