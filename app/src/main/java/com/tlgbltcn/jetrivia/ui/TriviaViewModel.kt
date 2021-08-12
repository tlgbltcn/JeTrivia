package com.tlgbltcn.jetrivia.ui

import com.tlgbltcn.jetrivia.data.model.Round
import com.tlgbltcn.jetrivia.data.repository.TriviaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@HiltViewModel
class TriviaViewModel @Inject constructor(private val repository: TriviaRepository) : ViewModel() {

    private var _trivia = MutableStateFlow(Round())
    val trivia = _trivia

    init {
        fetchTrivia()
    }

    fun fetchTrivia() = viewModelScope.launch {
        repository.fetchTriviaSet().collect {
            when (it) {
                is Round -> {
                    _trivia.value = it
                }
            }
        }
    }


}