package com.vpex.kmm.app.domain.async

sealed interface AsyncResult {
    data class Success<T>(val data: T) : AsyncResult
    data class Error(val exceptionMessage: String) : AsyncResult
    object Loading : AsyncResult
    object Uninitialized : AsyncResult
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> = Resource(status = Status.LOADING, data = data, message = null)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}