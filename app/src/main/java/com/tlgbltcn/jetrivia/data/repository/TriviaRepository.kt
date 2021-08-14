package com.tlgbltcn.jetrivia.data.repository

import com.tlgbltcn.jetrivia.data.remote.TriviaService
import com.tlgbltcn.jetrivia.util.ResultHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TriviaRepository @Inject constructor(
    private val remoteService: TriviaService
) {

    fun fetchTriviaSet() = flow {
        val response = remoteService.getQuestionSet()
        if (response.isSuccessful) {
            emit(ResultHolder.Success(response))
        } else {
            emit(response.errorBody())
        }
    }
        .onStart { emit(ResultHolder.Loading) }
        .flowOn(Dispatchers.IO)
}