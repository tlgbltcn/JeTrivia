package com.tlgbltcn.jetrivia.data.repository

import com.tlgbltcn.jetrivia.data.local.RoundDao
import com.tlgbltcn.jetrivia.data.local.TriviaDao
import com.tlgbltcn.jetrivia.data.model.Round
import com.tlgbltcn.jetrivia.data.model.Trivia
import com.tlgbltcn.jetrivia.data.remote.TriviaService
import com.tlgbltcn.jetrivia.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TriviaRepository @Inject constructor(
    private val remoteService: TriviaService,
    private val roundDao: RoundDao,
    private val triviaDao: TriviaDao
) {

    fun fetchTrivia() = flow {
        apiCall {
            remoteService.getQuestionSet()
        }.onOperation(
            onSuccess = data@{
                this@data.data.populateLocalDataSources()
                emit(
                    success(
                        roundDao
                            .getRoundsWithTrivia()
                            .last()
                    )
                )
            },

            onFailure = error@{
                emit(failure(this@error.message))
            },

            onLoading = {
                emit(loading())
            }
        )
    }
        .flowOn(Dispatchers.IO)

    private fun Round.populateLocalDataSources() {
        roundDao.insertRound(round = this).also { roundId ->
            this.trivias?.forEach {
                triviaDao.insertTrivia(
                    it.copy(roundCreatorId = roundId)
                )
            }
        }
    }

    fun updateJoker(joker: String) {
        val round = getActualRound()
        val jokers = mutableMapOf<String, Boolean>()
        round.jokers.forEach {
            if (it.key == joker) {
                jokers[it.key] = false
            } else {
                jokers[it.key] = it.value
            }
        }

        updateRound(round = round.copy(jokers = jokers))
    }

    fun finishRound() {
        val round = getActualRound()
        updateRound(round = round.copy(isCompleted = true))
    }

    fun updateTrivia(trivia: Trivia) {
        triviaDao.updateTrivia(trivia = trivia)
    }

    private fun updateRound(round: Round) {
        roundDao.updateRound(round = round)
    }

    private fun getActualRound() = roundDao.getActualRound()
}