package com.tlgbltcn.jetrivia.data.remote

import com.tlgbltcn.jetrivia.data.Round
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaService {

    @GET(value = "api.php")
    suspend fun getQuestionSet(
        @Query("amount") amount: Int? = 11,
        @Query("type") type: String? = "multiple"
    ): Response<Round>
}