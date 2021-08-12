package com.tlgbltcn.jetrivia.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tlgbltcn.jetrivia.ui.navigation.MainActions

@Composable
fun MainView(
    actions: MainActions,
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

        Text(text = "Main View")
    }
}