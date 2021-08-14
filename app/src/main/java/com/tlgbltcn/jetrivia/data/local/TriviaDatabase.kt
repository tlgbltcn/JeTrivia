package com.tlgbltcn.jetrivia.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tlgbltcn.jetrivia.data.model.Round
import com.tlgbltcn.jetrivia.data.model.Trivia

@Database(entities = [Round::class, Trivia::class], version = 1)
@TypeConverters(value = [Round.Converter::class, Trivia.Converter::class])
abstract class TriviaDatabase : RoomDatabase() {

    abstract fun roundDao(): RoundDao

    abstract fun triviaDao(): TriviaDao

    companion object {
        const val DATABASE_NAME = "database-trivia"
    }
}