package com.demo.amitav.testdrivendevelopmentapp.other

sealed class Resource<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(status = Status.SUCCESS, data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(status = Status.ERROR, data, message)
    class Loading<T> : Resource<T>(status = Status.LOADING)
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}