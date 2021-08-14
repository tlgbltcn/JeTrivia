package com.tlgbltcn.jetrivia.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlgbltcn.jetrivia.ui.screens.trivia.TriviaViewModel

@Composable
fun Timer(
    viewModel: TriviaViewModel,
    onFinished: () -> Unit
) {
    val state = viewModel.timer.collectAsState().value

    val columnModifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)

    val boxModifier = Modifier
        .size(50.dp)
        .clip(RoundedCornerShape(50.dp))
        .background(color = MaterialTheme.colors.primary.copy(alpha = 0.2f))

    Column(
        modifier = columnModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center, modifier = boxModifier) {
            CircularProgressIndicator(
                state / 10.toFloat()
            )
            Text(state.toString())
            if (state <= 0) {
                onFinished.invoke()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimer() {
    Timer(
        viewModel = hiltViewModel(),
        onFinished = {}
    )
}