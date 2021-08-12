package com.tlgbltcn.jetrivia.ui.screens.trivia

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tlgbltcn.jetrivia.data.model.Round
import com.tlgbltcn.jetrivia.ui.navigation.MainActions

@Composable
fun TriviaView(
    viewModel: TriviaViewModel,
    actions: MainActions,
) {

    // In Progress

    val round: Round = viewModel.trivia.collectAsState().value

    val modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "${round.trivias.toString()}")
    }
}