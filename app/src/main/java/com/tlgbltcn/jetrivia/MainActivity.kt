package com.tlgbltcn.jetrivia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import com.tlgbltcn.jetrivia.ui.navigation.NavGraph
import com.tlgbltcn.jetrivia.ui.theme.JeTriviaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JeTriviaTheme {
                NavGraph()
            }
        }
    }
}
