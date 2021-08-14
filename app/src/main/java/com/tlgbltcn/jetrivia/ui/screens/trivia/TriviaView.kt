package com.tlgbltcn.jetrivia.ui.screens.trivia

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.tlgbltcn.jetrivia.data.model.Round
import com.tlgbltcn.jetrivia.ui.component.Loading
import com.tlgbltcn.jetrivia.ui.navigation.MainActions
import com.tlgbltcn.jetrivia.util.ResultHolder

@Composable
fun TriviaView(
    viewModel: TriviaViewModel,
    actions: MainActions,
) {
    val context = LocalContext.current

    val round = viewModel.trivia.collectAsState().value

    val modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // In Progress

        when (round) {
            is ResultHolder.Loading -> {
                Loading()
            }

            is ResultHolder.Success -> {
                Text(text = "${round.data}")
            }

            is ResultHolder.Failure -> {
                showMessage(context = context, "${round.message}")
            }
        }
    }
}


fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}