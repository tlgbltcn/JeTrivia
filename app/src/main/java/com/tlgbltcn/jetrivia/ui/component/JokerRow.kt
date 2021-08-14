package com.tlgbltcn.jetrivia.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tlgbltcn.jetrivia.R

@Composable
fun JokerRow(
    onFiftyFiftyClicked: (() -> Unit)? = null,
    onSkipClicked: (() -> Unit)? = null,
    onExtraTimeClicked: (() -> Unit)? = null
) {

    val modifier = Modifier
        .fillMaxWidth()
        .padding(all = 20.dp)
        .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
        .background(color = MaterialTheme.colors.primary.copy(alpha = 0.2f))
        .padding(16.dp)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        JokerButton(text = R.string.fifty_fifty, onClick = onFiftyFiftyClicked)
        JokerButton(text = R.string.skip, onClick = onSkipClicked)
        JokerButton(text = R.string.extra_time, onClick = onExtraTimeClicked)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJokerRow() {
    JokerRow(onFiftyFiftyClicked = null, onSkipClicked = null, onExtraTimeClicked = null)
}