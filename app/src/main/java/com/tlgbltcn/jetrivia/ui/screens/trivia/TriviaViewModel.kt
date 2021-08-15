package com.tlgbltcn.jetrivia.ui.screens.trivia

import com.tlgbltcn.jetrivia.data.repository.TriviaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import androidx.lifecycle.*
import com.tlgbltcn.jetrivia.data.model.Trivia
import com.tlgbltcn.jetrivia.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@HiltViewModel
class TriviaViewModel @Inject constructor(private val repository: TriviaRepository) : ViewModel() {

    private var job: Job? = null

    private var _trivia = MutableStateFlow<ResultHolder<Trivia>>(Loading)
    val trivia = _trivia.asStateFlow()

    private var _isComplete = MutableStateFlow(false)
    val isComplete = _isComplete.asStateFlow()

    private var _timer = MutableStateFlow(9)
    val timer = _timer.asStateFlow()

    private var triviaList: MutableList<Trivia> = mutableListOf()

    private var questionBoundary = QUESTION_THRESHOLD

    private var index = 0

    init {
        fetchTrivia()
    }

    private fun fetchTrivia() = viewModelScope.launch {
        repository.fetchTrivia()
            .collect {
                when (it) {
                    is Loading -> {
                        _trivia.value = Loading
                    }

                    is Failure -> {
                        _trivia.value = Failure(it.message)
                    }

                    is Success -> {
                        triviaList.addAll(it.data.triviaList)
                        getNextQuestion()
                    }
                }
            }
    }

    fun getNextQuestion() {
        if (index < questionBoundary) {
            _trivia.value = Success(triviaList[index])
            index++
            startTimer()
        } else {
            finishRound()
        }
    }

    fun updateJoker(joker: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateJoker(
            joker = joker
        )
    }

    fun updateTrivia(trivia: Trivia) {
        viewModelScope.launch(Dispatchers.IO) {
            stopTimer()
            repository.updateTrivia(trivia = trivia)
            delay(SECOND)
            getNextQuestion()
        }
    }

    private fun finishRound() = viewModelScope.launch(Dispatchers.IO) {
        _isComplete.value = true
        repository.finishRound()
    }

    private fun startTimer(
        time: Int = 9
    ) {
        job = viewModelScope.launch {
            (time downTo 0)
                .asFlow()
                .onEach { delay(SECOND) }
                .onStart { emit(time) }
                .collect { _timer.emit(it) }
        }
    }

    fun fiftyFifty() {
        val trivia = triviaList[index - 1]
        val restrictedList = trivia.incorrectAnswers.shuffled().subList(0, 1)
        _trivia.value = Success(
            trivia.copy(incorrectAnswers = restrictedList)
        )
    }

    fun skip() {
        stopTimer()
        questionBoundary++
        getNextQuestion()
    }

    fun addExtraTime(extraTime: Int = EXTRA_TIME) {
        val latestTime = timer.value
        stopTimer()
        startTimer(time = latestTime + extraTime)
    }

    private fun stopTimer() = job?.cancel()

    companion object {
        const val QUESTION_THRESHOLD = 10
        const val SECOND = 1000L
        const val EXTRA_TIME = 10
    }
}