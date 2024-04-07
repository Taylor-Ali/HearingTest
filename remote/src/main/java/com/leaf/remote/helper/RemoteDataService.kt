package com.leaf.remote.helper

import android.content.Context
import com.google.gson.Gson
import com.leaf.remote.response.ErrorResponse
import com.leaf.remote.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * RemoteDataService
 * handles the api the request and response made to the api and encapsulates the date return within a resource object
 */
abstract class RemoteDataService(
    private val context: Context,
    private val dispatcher: Dispatchers,
    private val gson: Gson,
) {
    /**
     * handleRemoteDataCall
     * handles the api call response and wraps the data returned into resource object
     */
    suspend fun <T> handleRemoteDataCall(remoteDataCall: suspend () -> Response<T>): Resource<T> {
        return withContext(dispatcher.IO) {
            try {
                Resource.Loading(null)
                delay(DELAY)
                val response: Response<T> = remoteDataCall()

                if (response.isSuccessful) {
                    Resource.Success(
                        code = response.code(),
                        response.message(),
                        data = response.body()!!
                    )

                } else {
                    val errorResponse: ErrorResponse? = convertErrorBody(response.errorBody())
                    Resource.Error(
                        code = errorResponse?.code ?: 0,
                        errorMessage = errorResponse?.message
                            ?: context.getString(R.string.api_error_something_went_wrong)
                    )
                }

            } catch (e: HttpException) {
                Resource.Error(
                    code = e.code(),
                    errorMessage = e.message
                        ?: context.getString(R.string.api_error_something_went_wrong)
                )
            } catch (e: IOException) {
                Resource.Error(errorMessage = context.getString(R.string.api_error_please_check_your_network_connection))
            } catch (e: Exception) {
                Resource.Error(errorMessage = context.getString(R.string.api_error_something_went_wrong))
            }
        }
    }

    private fun convertErrorBody(errorBody: ResponseBody?): ErrorResponse? {
        return try {
            errorBody?.string()?.let {
                return gson.fromJson(it, ErrorResponse::class.java)
            }
        } catch (exception: Exception) {
            null
        }
    }

    companion object {
        const val DELAY = 1000L
    }
}