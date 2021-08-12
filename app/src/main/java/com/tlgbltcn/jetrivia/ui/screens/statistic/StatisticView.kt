package com.tlgbltcn.jetrivia.ui.screens.statistic


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tlgbltcn.jetrivia.ui.navigation.MainActions

@Composable
fun StatisticView(
    actions: MainActions
) {

    // In Progress

    val modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Statistic View")
    }
}