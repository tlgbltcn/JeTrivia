package com.tlgbltcn.jetrivia.data.local

import androidx.room.*
import com.tlgbltcn.jetrivia.data.model.Trivia

@Dao
interface TriviaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrivia(trivia: Trivia)

    @Update
    fun updateTrivia(trivia: Trivia)

    @Query("SELECT * FROM trivia")
    fun getTriviaList(): List<Trivia>
}