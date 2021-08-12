package com.tlgbltcn.jetrivia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.tlgbltcn.jetrivia.data.model.Round
import com.tlgbltcn.jetrivia.ui.TriviaViewModel
import com.tlgbltcn.jetrivia.ui.theme.JeTriviaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JeTriviaTheme {
                val viewModel: TriviaViewModel = hiltViewModel<TriviaViewModel>()
                val round: Round = viewModel.trivia.collectAsState().value

                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android", round)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, round: Round) {
    Text(text = "${round.trivias.toString()}")
}