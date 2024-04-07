package com.leaf.remote.helper

sealed class Resource<T>(
    val code: Int? = 0,
    val message: String? = "",
    val data: T? = null,
) {
    class Success<T>(code: Int? = 0, message: String?, data: T? = null) :
        Resource<T>(code = code, message = message, data = data)

    class Loading<T>(data: T? = null) :
        Resource<T>(data = data)

    class Error<T>(code: Int? = null, errorMessage: String, data: T? = null) :
        Resource<T>(code = code, message = errorMessage, data = data)
}
