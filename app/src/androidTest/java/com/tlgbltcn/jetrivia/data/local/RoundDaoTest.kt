package com.tlgbltcn.jetrivia.data.local


import com.tlgbltcn.jetrivia.data.model.Round
import com.google.common.truth.Truth
import com.tlgbltcn.jetrivia.data.model.RoundAndTrivia
import org.junit.Test

class RoundDaoTest : BaseDatabaseTest() {

    private lateinit var roundDao: RoundDao

    private val joker = mutableMapOf(
        Round.FIFTY_FIFTY to true,
        Round.SKIP to true,
        Round.EXTRA_TIME to true
    )

    private val rounds = listOf(
        Round(
            roundId = 1,
            trivias = listOf(),
            isCompleted = false,
            jokers = joker
        ),
        Round(
            roundId = 2,
            trivias = listOf(),
            isCompleted = true,
            jokers = joker
        ),
        Round(
            roundId = 3,
            trivias = listOf(),
            isCompleted = false,
            jokers = joker
        ),
    )

    override fun fillDao() {
        super.fillDao()
        roundDao = database.roundDao()
        rounds.forEach(roundDao::insertRound)
    }

    @Test
    fun testRecordedCount() {
        // Then
        Truth.assertThat(
            roundDao.getRoundsWithTrivia().size
        ).isEqualTo(
            3
        )
    }

    @Test
    fun testGetActualRound() {

        // When
        val value: Round = roundDao.getActualRound()

        // Then
        Truth.assertThat(value).isEqualTo(rounds.last())
    }

    @Test
    fun testGetRounds() {

        // When
        val value: List<RoundAndTrivia> = roundDao.getRoundsWithTrivia()

        // Then
        Truth.assertThat(
            value.map {
                it.round
            })
            .isEqualTo(
                rounds.map {
                    it
                })
    }

    @Test
    fun testUpdateRound() {
        // Given
        val round = Round(
            roundId = 10,
            trivias = listOf(),
            isCompleted = false,
            jokers = mapOf()
        )

        // When
        roundDao.insertRound(round = round)
        roundDao.updateRound(round = round.copy(isCompleted = true))

        // Then
        val actualRound = roundDao.getActualRound()
        Truth.assertThat(
            actualRound.isCompleted
        ).isNotEqualTo(
            round.isCompleted
        )
    }
}