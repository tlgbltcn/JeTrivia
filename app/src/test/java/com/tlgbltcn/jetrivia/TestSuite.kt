package com.tlgbltcn.jetrivia

import android.os.Build
import org.robolectric.annotation.Config
import com.tlgbltcn.jetrivia.data.local.*
import com.tlgbltcn.jetrivia.data.remote.TriviaServiceTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalSerializationApi
@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(Suite::class)
@Suite.SuiteClasses(
    RoundDaoTest::class,
    TriviaDaoTest::class,
    TriviaServiceTest::class
)
class TestSuite