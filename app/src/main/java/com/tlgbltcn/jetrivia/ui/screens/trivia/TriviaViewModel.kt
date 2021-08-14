package com.tlgbltcn.jetrivia.ui.screens.trivia

import com.tlgbltcn.jetrivia.data.model.Round
import com.tlgbltcn.jetrivia.data.repository.TriviaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import androidx.lifecycle.*
import com.tlgbltcn.jetrivia.util.ResultHolder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@HiltViewModel
class TriviaViewModel @Inject constructor(private val repository: TriviaRepository) : ViewModel() {

    private var _trivia = MutableStateFlow<ResultHolder>(ResultHolder.Loading)
    val trivia = _trivia

    init {
        fetchTrivia()
    }

    private fun fetchTrivia() = viewModelScope.launch {
        repository.fetchTriviaSet().collect {
            when (it) {
                is ResultHolder.Loading -> {
                    _trivia.value = ResultHolder.Loading
                }

                is ResultHolder.Failure -> {
                    _trivia.value = ResultHolder.Failure(it.message)
                }

                is ResultHolder.Success -> {
                    _trivia.value = ResultHolder.Success(it.data)
                }
            }
        }
    }
}