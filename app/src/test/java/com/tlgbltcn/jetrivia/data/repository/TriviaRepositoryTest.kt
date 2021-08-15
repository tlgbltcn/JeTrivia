package com.tlgbltcn.jetrivia.data.repository

import com.google.common.truth.Truth
import com.tlgbltcn.jetrivia.data.local.RoundDao
import com.tlgbltcn.jetrivia.data.local.TriviaDao
import com.tlgbltcn.jetrivia.data.model.Round
import com.tlgbltcn.jetrivia.data.model.RoundAndTrivia
import com.tlgbltcn.jetrivia.data.model.Trivia
import com.tlgbltcn.jetrivia.data.remote.TriviaService
import com.tlgbltcn.jetrivia.util.Success
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response.success

class TriviaRepositoryTest {

    @MockK
    lateinit var remoteService: TriviaService

    @MockK
    lateinit var roundDao: RoundDao

    @MockK
    lateinit var triviaDao: TriviaDao

    private lateinit var repository: TriviaRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = TriviaRepository(
            remoteService = remoteService,
            roundDao = roundDao,
            triviaDao = triviaDao
        )
    }

    @InternalCoroutinesApi
    @Test
    fun `flow emits successfully`() = runBlocking {

        // Given
        val round = Round(
            roundId = 1,
            trivias = listOf(),
            isCompleted = false,
            jokers = mapOf()
        )

        coEvery { remoteService.getQuestionSet() } answers {
            success(
                round
            )
        }

        coEvery {
            roundDao.insertRound(round = round)
        } returns 1L

        coEvery {
            triviaDao.insertTrivia(
                trivia = Trivia(
                    triviaId = 1,
                    roundCreatorId = 1,
                    category = "",
                    correctAnswer = "",
                    difficulty = "",
                    incorrectAnswers = listOf(),
                    question = "",
                    type = ""
                )
            )
        } returns Unit

        coEvery {
            roundDao.getRoundsWithTrivia()
        } returns listOf(RoundAndTrivia(round = round, triviaList = listOf()))

        // When
        val flow = repository.fetchTrivia()

        // Then
        flow.collect {
            Truth.assertThat((it as Success).data.round).isEqualTo(round)
        }
    }
}