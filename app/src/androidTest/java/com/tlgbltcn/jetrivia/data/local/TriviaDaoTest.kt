package com.tlgbltcn.jetrivia.data.local


import com.tlgbltcn.jetrivia.data.model.Round
import com.google.common.truth.Truth
import com.tlgbltcn.jetrivia.data.model.RoundAndTrivia
import com.tlgbltcn.jetrivia.data.model.Trivia
import org.junit.Test

class TriviaDaoTest : BaseDatabaseTest() {

    private lateinit var roundDao: RoundDao
    private lateinit var triviaDao: TriviaDao

    private val joker = mutableMapOf(
        Round.FIFTY_FIFTY to true,
        Round.SKIP to true,
        Round.EXTRA_TIME to true
    )

    private val trivia =
        Trivia(
            triviaId = 1,
            roundCreatorId = 1,
            category = "Entertainment: Video Games",
            correctAnswer = "Charcoal",
            difficulty = "easy",
            incorrectAnswers = listOf(),
            question = "In vanilla Minecraft, which of the following cannot be made into a block?",
            type = "multiple",
            status = Trivia.Status.IDLE
        )

    private val round = Round(
        roundId = 1, trivias = listOf(), isCompleted = false, jokers = joker
    )

    override fun fillDao() {
        super.fillDao()
        roundDao = database.roundDao()
        triviaDao = database.triviaDao()
        roundDao.insertRound(round = round)
        triviaDao.insertTrivia(trivia = trivia.copy(roundCreatorId = round.roundId))
    }

    @Test
    fun testRecordedCount() {
        // Then
        Truth.assertThat(
            triviaDao.getTriviaList().size
        ).isEqualTo(
            1
        )
    }

    @Test
    fun testUpdateTrivia() {
        // Given
        val round = Round(
            roundId = 10,
            trivias = listOf(),
            isCompleted = false,
            jokers = mapOf()
        )

        val trivia = Trivia(
            triviaId = 10,
            roundCreatorId = 10,
            category = "Science: Computers",
            correctAnswer = "Overloading",
            difficulty = "medium",
            incorrectAnswers = listOf(
                "Overriding",
                "Abstracting",
                "Inheriting"
            ),
            question = "In programming, what do you call functions with the same name but different implementations?",
            type = "multiple",
            status = Trivia.Status.IDLE
        )

        // When
        roundDao.insertRound(round = round)
        triviaDao.insertTrivia(trivia = trivia)
        triviaDao.updateTrivia(trivia = trivia.copy(status = Trivia.Status.CORRECT))

        // Then
        val triviaList = triviaDao.getTriviaList()
        Truth.assertThat(
            triviaList.last().status
        ).isEqualTo(
            Trivia.Status.CORRECT
        )
    }
}