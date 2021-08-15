package com.tlgbltcn.jetrivia.ui.screens.trivia

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tlgbltcn.jetrivia.R
import com.tlgbltcn.jetrivia.data.model.Round.Companion.EXTRA_TIME
import com.tlgbltcn.jetrivia.data.model.Round.Companion.FIFTY_FIFTY
import com.tlgbltcn.jetrivia.data.model.Round.Companion.SKIP
import com.tlgbltcn.jetrivia.data.model.Trivia
import com.tlgbltcn.jetrivia.ui.component.*
import com.tlgbltcn.jetrivia.ui.navigation.MainActions
import com.tlgbltcn.jetrivia.util.ResultHolder

@Composable
fun TriviaView(
    viewModel: TriviaViewModel,
    actions: MainActions,
) {
    val round = viewModel.trivia.collectAsState().value

    viewModel.isComplete.collectAsState().also {
        if (it.value) actions.navigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.trivia_screen)) },
                backgroundColor = Color.White,
                elevation = 4.dp,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            actions.navigateBack()
                        }
                    ) {
                        val backIcon: Painter =
                            painterResource(id = R.drawable.ic_baseline_arrow_back_24)
                        Icon(
                            painter = backIcon,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            )
        },
    ) {
        when (round) {
            is ResultHolder.Loading -> {
                Loading()
            }

            is ResultHolder.Success -> {
                Column {
                    Timer(
                        viewModel = viewModel,
                        onFinished = {
                            viewModel.getNextQuestion()
                        }
                    )

                    JokerRow(
                        onFiftyFiftyClicked = {
                            viewModel.updateJoker(FIFTY_FIFTY)
                            viewModel.fiftyFifty()
                        },
                        onSkipClicked = {
                            viewModel.updateJoker(SKIP)
                            viewModel.skip()
                        },
                        onExtraTimeClicked = {
                            viewModel.updateJoker(EXTRA_TIME)
                            viewModel.addExtraTime()
                        }
                    )
                    Question(
                        data = round.data,
                        onAnswerSelected = { trivia: Trivia, answer: Boolean ->
                            viewModel.updateTrivia(
                                trivia.copy(
                                    status =
                                    if (answer) Trivia.Status.CORRECT
                                    else Trivia.Status.WRONG
                                )
                            )
                        }
                    )
                }
            }

            is ResultHolder.Failure -> {
                ErrorBox(message = round.message)
            }
        }
    }
}