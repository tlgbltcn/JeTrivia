package com.tlgbltcn.jetrivia.util

import retrofit2.Response

// In Progress

sealed class ResultHolder {
    data class Success(val data: Response<*>) : ResultHolder()
    data class Failure(val message: String) : ResultHolder()
    object Loading : ResultHolder()
}
