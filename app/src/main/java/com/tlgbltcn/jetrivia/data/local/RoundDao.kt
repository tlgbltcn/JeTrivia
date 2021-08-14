package com.tlgbltcn.jetrivia.data.local

import androidx.room.*
import com.tlgbltcn.jetrivia.data.model.Round
import com.tlgbltcn.jetrivia.data.model.RoundAndTrivia

@Dao
interface RoundDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRound(round: Round): Long

    @Update
    fun updateRound(round: Round)

    @Query("SELECT * FROM round ORDER BY round_id DESC LIMIT 1")
    fun getActualRound(): Round

    @Transaction
    @Query("SELECT * FROM round")
    fun getRoundsWithTrivia(): List<RoundAndTrivia>
}