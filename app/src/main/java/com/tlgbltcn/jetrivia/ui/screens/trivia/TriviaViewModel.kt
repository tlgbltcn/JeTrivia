package com.tlgbltcn.jetrivia.ui.screens.trivia

import com.tlgbltcn.jetrivia.data.repository.TriviaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import androidx.lifecycle.*
import com.tlgbltcn.jetrivia.data.model.Trivia
import com.tlgbltcn.jetrivia.util.ResultHolder
import com.tlgbltcn.jetrivia.util.failure
import com.tlgbltcn.jetrivia.util.loading
import com.tlgbltcn.jetrivia.util.success
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@HiltViewModel
class TriviaViewModel @Inject constructor(private val repository: TriviaRepository) : ViewModel() {

    private var _trivia = MutableStateFlow<ResultHolder<Trivia>>(ResultHolder.Loading)
    val trivia = _trivia.asStateFlow()

    private var triviaList: MutableList<Trivia> = mutableListOf()

    private var index = 0

    init {
        fetchTrivia()
    }

    private fun fetchTrivia() = viewModelScope.launch {
        repository.fetchTriviaSet().collect {
            when (it) {
                is ResultHolder.Loading -> {
                    _trivia.value = loading()
                }

                is ResultHolder.Failure -> {
                    _trivia.value = failure(it.message)
                }

                is ResultHolder.Success -> {
                    triviaList.addAll(it.data.last().triviaList)
                    _trivia.value = success(triviaList[index])
                }
            }
        }
    }
}