package com.tlgbltcn.jetrivia.ui.screens.statistic

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tlgbltcn.jetrivia.R
import com.tlgbltcn.jetrivia.data.model.Round
import com.tlgbltcn.jetrivia.data.model.Round.Companion.EXTRA_TIME
import com.tlgbltcn.jetrivia.data.model.Round.Companion.FIFTY_FIFTY
import com.tlgbltcn.jetrivia.data.model.Round.Companion.SKIP
import com.tlgbltcn.jetrivia.data.model.RoundAndTrivia
import com.tlgbltcn.jetrivia.data.model.Trivia
import com.tlgbltcn.jetrivia.ui.component.ItemDivider
import com.tlgbltcn.jetrivia.ui.component.JokerButton
import com.tlgbltcn.jetrivia.ui.navigation.MainActions

@Composable
fun StatisticView(
    viewModel: StatisticViewModel,
    actions: MainActions
) {

    val modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)

    val rounds: List<RoundAndTrivia> = viewModel.rounds.collectAsState().value

    var roundItem: Round? = null

    val selectedRound = remember { mutableStateOf(roundItem) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.statistic_screen)
                    )
                },
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
        LazyColumn(contentPadding = PaddingValues(bottom = 8.dp), modifier = modifier) {
            items(items = rounds) { item ->

                val animatedProgress = remember { Animatable(initialValue = 0.2f) }

                LaunchedEffect(Unit) {
                    animatedProgress.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(300, easing = LinearOutSlowInEasing)
                    )
                }

                val isRoundSelected = item.round == selectedRound.value

                val roundBorderColor = if (item.round.isCompleted) {
                    colorResource(id = R.color.teal_200).copy(0.2f)
                } else {
                    colorResource(id = R.color.red_200).copy(0.2f)
                }

                val roundBackgroundColor = if (item.round.isCompleted) {
                    colorResource(id = R.color.teal_200).copy(0.2f)
                } else {
                    colorResource(id = R.color.red_200).copy(0.2f)
                }

                Surface(
                    shape = MaterialTheme.shapes.small,
                    border = BorderStroke(
                        width = 1.dp,
                        color = roundBorderColor
                    ),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .graphicsLayer(
                            scaleY = animatedProgress.value,
                            scaleX = animatedProgress.value
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = isRoundSelected,
                                onClick = {
                                    roundItem = item.round
                                    selectedRound.value = roundItem
                                }
                            )
                            .background(roundBackgroundColor)
                            .padding(vertical = 20.dp, horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "#${item.round.roundId.toString()} " +
                                        stringResource(id = R.string.round)
                            )
                            if (isRoundSelected) {
                                Column(
                                    modifier = Modifier.fillMaxWidth()

                                ) {

                                    Text(
                                        text = stringResource(id = R.string.jokers),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp, bottom = 4.dp)
                                    )

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                                            .background(
                                                color = MaterialTheme.colors.primary.copy(
                                                    alpha = 0.2f
                                                )
                                            )
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.spacedBy(
                                            space = 16.dp,
                                            alignment = Alignment.CenterHorizontally
                                        )
                                    ) {
                                        JokerButton(
                                            text = R.string.fifty_fifty,
                                            isPreview = true,
                                            previewValue = item.round.jokers[FIFTY_FIFTY] ?: false
                                        )
                                        JokerButton(
                                            text = R.string.skip,
                                            isPreview = true,
                                            previewValue = item.round.jokers[SKIP] ?: false
                                        )
                                        JokerButton(
                                            text = R.string.extra_time,
                                            isPreview = true,
                                            previewValue = item.round.jokers[EXTRA_TIME] ?: false
                                        )
                                    }

                                    val list = item.triviaList
                                    list.forEachIndexed { index, triviaItem ->

                                        ItemDivider()

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = stringResource(
                                                    id = R.string.question,
                                                    "${index + 1}"
                                                ),
                                                style = MaterialTheme.typography.caption
                                            )
                                            Image(
                                                painter = when (triviaItem.status) {
                                                    Trivia.Status.CORRECT -> painterResource(id = R.drawable.ic_baseline_check_24)
                                                    Trivia.Status.WRONG -> painterResource(id = R.drawable.ic_baseline_close_24)
                                                    Trivia.Status.IDLE -> painterResource(id = R.drawable.ic_baseline_horizontal_rule_24)
                                                },
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}