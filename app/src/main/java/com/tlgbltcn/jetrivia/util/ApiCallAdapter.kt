package com.tlgbltcn.jetrivia.util

import retrofit2.Response
import java.lang.Exception


suspend fun <T> apiCall(block: suspend () -> Response<T>): ResultHolder<T> {
    return try {
        val response = block.invoke()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                Success(body)
            }
                ?: Failure(message = "code: ${response.code()} message: ${response.message()}")
        } else {
            Failure(
                message = "code: ${response.code()} message: ${response.message()}"
            )
        }
    } catch (e: Exception) {
        Failure(message = "${e.message} ")
    }
}