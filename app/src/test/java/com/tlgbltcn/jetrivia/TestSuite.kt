package com.tlgbltcn.jetrivia

import android.os.Build
import org.robolectric.annotation.Config
import com.tlgbltcn.jetrivia.data.local.*
import org.junit.runner.RunWith
import org.junit.runners.Suite

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(Suite::class)
@Suite.SuiteClasses(
    RoundDaoTest::class,
    TriviaDaoTest::class
)
class TestSuite