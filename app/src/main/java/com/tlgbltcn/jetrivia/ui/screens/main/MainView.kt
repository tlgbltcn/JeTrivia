package com.tlgbltcn.jetrivia.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tlgbltcn.jetrivia.R
import com.tlgbltcn.jetrivia.ui.navigation.MainActions

@Composable
fun MainView(
    actions: MainActions,
) {


    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name)
                    )
                },
                backgroundColor = Color.White,
                elevation = 4.dp
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val mainButtonColor = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.surface
            )

            Button(
                onClick = {
                    actions.goToTrivia()
                },
                colors = mainButtonColor
            ) {
                Text(
                    text = stringResource(id = R.string.play)
                )
            }

            Spacer(
                modifier = Modifier.padding(all = 8.dp)
            )

            Button(
                onClick = {
                    actions.gotoStatistics()
                },
                colors = mainButtonColor
            ) {
                Text(
                    text = stringResource(id = R.string.statistic_screen)
                )
            }
        }
    }
}