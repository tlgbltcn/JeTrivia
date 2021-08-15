package com.tlgbltcn.jetrivia.data.remote

import com.google.common.truth.Truth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tlgbltcn.jetrivia.data.model.Round
import com.tlgbltcn.jetrivia.data.model.Trivia
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class TriviaServiceTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(TriviaService::class.java)


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch movies correctly given 200 response`() = runBlocking {
        // Given
        mockWebServer.enqueueResponse("response.json", 200)

        // When
        val actual = api.getQuestionSet()

        // Then
        Truth.assertThat(actual.body()).isEqualTo(expected)
    }

    private fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        javaClass.classLoader?.getResourceAsStream(fileName)
            ?.source()
            ?.buffer()
            .also { source ->
                source?.run {
                    enqueue(
                        MockResponse()
                            .setResponseCode(code)
                            .setBody(source.readString(StandardCharsets.UTF_8))
                    )
                }
            }
    }
}

val expected = Round(
    trivias = listOf(
        Trivia(
            category = "Entertainment: Music",
            correctAnswer = "Syd Barrett",
            difficulty = "medium",
            incorrectAnswers = listOf(
                "John Lennon",
                "David Gilmour",
                "Floyd"
            ),
            question = "Who is the Pink Floyd song &quot;Shine On You Crazy Diamond&quot; written about?",
            type = "multiple"
        )
    )
)