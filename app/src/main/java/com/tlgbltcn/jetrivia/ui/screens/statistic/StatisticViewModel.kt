package com.tlgbltcn.jetrivia.ui.screens.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlgbltcn.jetrivia.data.model.RoundAndTrivia
import com.tlgbltcn.jetrivia.data.repository.TriviaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(private val repository: TriviaRepository) :
    ViewModel() {

    private val _rounds = MutableStateFlow(listOf<RoundAndTrivia>())
    val rounds = _rounds.asStateFlow()

    init {
        getRounds()
    }

    private fun getRounds() = viewModelScope.launch(Dispatchers.IO) {
        val roundAndTrivia = repository.getRounds()
        _rounds.value = roundAndTrivia
    }
}