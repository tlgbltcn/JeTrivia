package com.tlgbltcn.jetrivia.ui.navigation

import androidx.annotation.StringRes
import com.tlgbltcn.jetrivia.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Main : Screen("main_screen", R.string.main_screen)
    object Trivia : Screen("trivia_screen", R.string.trivia_screen)
    object Statistics : Screen("statistics_screen", R.string.statistic_screen)
}
