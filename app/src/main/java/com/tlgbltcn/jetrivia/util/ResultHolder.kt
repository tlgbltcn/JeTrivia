package com.tlgbltcn.jetrivia.util


typealias Loading = ResultHolder.Loading
typealias Success<T> = ResultHolder.Success<T>
typealias Failure = ResultHolder.Failure

sealed class ResultHolder<out T> {
    data class Success<T>(val data: T) : ResultHolder<T>()
    object Loading : ResultHolder<Nothing>()
    data class Failure(val message: String? = null) : ResultHolder<Nothing>()
}

suspend fun <T> ResultHolder<T>.onOperation(
    onSuccess: suspend Success<T>.() -> Unit,
    onFailure: suspend Failure.() -> Unit,
    onLoading: suspend (() -> Unit)
) {
    when (this) {
        is Success -> {
            onSuccess.invoke(this)
        }

        is Failure -> {
            onFailure.invoke(this)
        }

        is Loading -> {
            onLoading.invoke()
        }
    }
}