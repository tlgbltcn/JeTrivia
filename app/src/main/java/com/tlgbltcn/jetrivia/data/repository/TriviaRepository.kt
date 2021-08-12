package com.tlgbltcn.jetrivia.data.repository

import com.tlgbltcn.jetrivia.data.remote.TriviaService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TriviaRepository @Inject constructor(
    private val remoteService: TriviaService
    ) {

    fun fetchTriviaSet() = flow {
        val response = remoteService.getQuestionSet()
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            emit(response.errorBody())
        }
    }.flowOn(Dispatchers.IO)
}