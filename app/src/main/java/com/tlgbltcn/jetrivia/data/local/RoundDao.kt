package com.tlgbltcn.jetrivia.data.local

import androidx.room.*
import com.tlgbltcn.jetrivia.data.model.Round

@Dao
interface RoundDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRound(round: Round): Long

    @Update
    fun updateRound(round: Round)
}