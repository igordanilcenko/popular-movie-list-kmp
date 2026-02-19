package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.moviedetail.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.previewReviewLong
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.previewReviewShort
import com.ihardanilchanka.sampleappkmp.android.core.ui.resource.AppTheme
import com.ihardanilchanka.sampleappkmp.domain.model.Review

@Composable
fun ReviewItem(
    review: Review,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = review.author,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = review.content,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewItemShortPreview() {
    AppTheme {
        ReviewItem(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            review = previewReviewShort,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewItemLongPreview() {
    AppTheme {
        ReviewItem(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            review = previewReviewLong,
        )
    }
}
