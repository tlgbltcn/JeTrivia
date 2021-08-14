package com.tlgbltcn.jetrivia.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.tlgbltcn.jetrivia.R
import com.tlgbltcn.jetrivia.data.model.Trivia

@Composable
fun Question(
    data: Trivia,
    onAnswerSelected: (Trivia, Boolean) -> Unit
) {
    val columnModifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)

    val textModifier = Modifier.padding(bottom = 8.dp, start = 10.dp, end = 10.dp)

    val options = data.incorrectAnswers
        .associateWith { false }
        .toMutableMap()
        .also { it[data.correctAnswer] = true }

    val optionList = options.toList().shuffled()

    var answer: Map<String, Boolean>? = null

    val selectedOption = remember { mutableStateOf(answer) }

    val enabled = remember(data) { mutableStateOf(true) }

    Column(modifier = columnModifier) {

        Text(
            text = data.question,
            modifier = textModifier
        )

        LazyColumn(contentPadding = PaddingValues(bottom = 8.dp)) {
            items(items = optionList) { item ->

                val optionSelected = mapOf(item.first to item.second) == selectedOption.value

                val answerBorderColor = if (optionSelected && item.second) {
                    colorResource(id = R.color.teal_200).copy(0.2f)
                } else if (optionSelected && !item.second) {
                    colorResource(id = R.color.red_200).copy(0.2f)
                } else {
                    MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                }

                val answerBackgroundColor = if (optionSelected && item.second) {
                    colorResource(id = R.color.teal_200).copy(0.2f)
                } else if (optionSelected && !item.second) {
                    colorResource(id = R.color.red_200).copy(0.2f)
                } else {
                    MaterialTheme.colors.background
                }

                Surface(
                    shape = MaterialTheme.shapes.small,
                    border = BorderStroke(
                        width = 1.dp,
                        color = answerBorderColor
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = optionSelected,
                                enabled = enabled.value,
                                onClick = {
                                    enabled.value = false
                                    answer = mapOf(item.first to item.second)
                                    selectedOption.value = answer
                                    options[item.first]?.let {
                                        onAnswerSelected(data, it)
                                    }
                                }
                            )
                            .background(answerBackgroundColor)
                            .padding(vertical = 20.dp, horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.first
                        )
                    }
                }
            }
        }
    }
}