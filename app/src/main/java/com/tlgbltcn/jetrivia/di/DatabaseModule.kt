package com.tlgbltcn.jetrivia.di

import android.content.Context
import androidx.room.Room
import com.tlgbltcn.jetrivia.data.local.RoundDao
import com.tlgbltcn.jetrivia.data.local.TriviaDao
import com.tlgbltcn.jetrivia.data.local.TriviaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): TriviaDatabase {
        return Room.databaseBuilder(
            context,
            TriviaDatabase::class.java,
            TriviaDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesRoundDao(database: TriviaDatabase): RoundDao {
        return database.roundDao()
    }

    @Provides
    fun providesTriviaDao(database: TriviaDatabase): TriviaDao {
        return database.triviaDao()
    }
}