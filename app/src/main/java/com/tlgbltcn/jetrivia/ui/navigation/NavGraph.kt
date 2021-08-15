package com.tlgbltcn.jetrivia.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tlgbltcn.jetrivia.ui.screens.main.MainView
import com.tlgbltcn.jetrivia.ui.screens.trivia.TriviaView
import com.tlgbltcn.jetrivia.ui.screens.statistic.StatisticView
import com.tlgbltcn.jetrivia.ui.screens.statistic.StatisticViewModel
import com.tlgbltcn.jetrivia.ui.screens.trivia.TriviaViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    NavHost(navController, startDestination = Screen.Main.route) {

        composable(Screen.Main.route) {
            MainView(
                actions = actions
            )
        }

        composable(Screen.Trivia.route) {
            val viewModel: TriviaViewModel = viewModel(
                factory = HiltViewModelFactory(LocalContext.current, it)
            )
            TriviaView(
                viewModel = viewModel,
                actions = actions
            )
        }

        composable(Screen.Statistics.route) {
            val viewModel: StatisticViewModel = viewModel(
                factory = HiltViewModelFactory(LocalContext.current, it)
            )
            StatisticView(
                viewModel = viewModel,
                actions = actions
            )
        }
    }
}

class MainActions(navController: NavHostController) {

    val gotoStatistics: () -> Unit = {
        navController.navigate(Screen.Statistics.route)
    }

    val goToTrivia: () -> Unit = {
        navController.navigate(Screen.Trivia.route)
    }

    val navigateBack: () -> Unit = {
        navController.navigateUp()
    }
}