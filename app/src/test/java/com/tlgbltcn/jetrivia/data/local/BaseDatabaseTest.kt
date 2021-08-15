package com.tlgbltcn.jetrivia.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class BaseDatabaseTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    lateinit var database: TriviaDatabase

    @Before
    fun createDatabase() = runBlocking {
        database =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                TriviaDatabase::class.java
            )
                .allowMainThreadQueries()
                .build()

        fillDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    open fun fillDao() {
        // no-op
    }
}