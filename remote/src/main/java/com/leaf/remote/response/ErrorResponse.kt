package com.leaf.remote.response

import com.google.gson.annotations.SerializedName

/**
 * ErrorResponse
 * Response class used to map any errors thrown by the api response
 * @param code [Int] represents the error code.
 * @param message [String] represents the message.
 */
data class ErrorResponse(
    val code: Int? = 0,
    @SerializedName("error")
    val message: String
)