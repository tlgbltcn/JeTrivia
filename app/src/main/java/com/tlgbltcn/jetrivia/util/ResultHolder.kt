package com.tlgbltcn.jetrivia.util


sealed class ResultHolder<out T> {
    data class Success<T>(val data: T) : ResultHolder<T>()
    object Loading : ResultHolder<Nothing>()
    data class Failure(val message: String? = null) : ResultHolder<Nothing>()
}

suspend fun <T> ResultHolder<T>.onOperation(
    onSuccess: suspend ResultHolder.Success<T>.() -> Unit,
    onFailure: suspend ResultHolder.Failure.() -> Unit,
    onLoading: suspend (() -> Unit)
) {
    when (this) {
        is ResultHolder.Success -> {
            onSuccess.invoke(this)
        }

        is ResultHolder.Failure -> {
            onFailure.invoke(this)
        }

        is ResultHolder.Loading -> {
            onLoading.invoke()
        }
    }
}

fun <T> success(data: T): ResultHolder<T> {
    return ResultHolder.Success(data)
}

fun failure(message: String?): ResultHolder<Nothing> {
    return ResultHolder.Failure(message = message)
}

fun loading() = ResultHolder.Loading