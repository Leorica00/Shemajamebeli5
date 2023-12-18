package com.example.shemajamebeli5

import android.util.Log
import com.example.shemajamebeli5.api.RetrofitClient
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeoutException

sealed class AppError(open val message: String) {
    data class NetworkError(override val message: String) : AppError(message)
    data class HttpError(override val message: String) : AppError(message)
    data class TimeoutError(override val message: String) : AppError(message)
    data class ServerError(override val message: String) : AppError(message)
    data class ClientError(override val message: String) : AppError(message)
    data class UnknownError(override val message: String) : AppError(message)

    companion object {
        fun fromException(throwable: Throwable): AppError {
            return when (throwable) {
                is IOException -> NetworkError("Network error occurred: No Internet")
                is HttpException -> {
                    when (throwable.code()) {
                        in 400..499 -> {
                            val errorModel = throwable.response()?.errorBody()?.string()
                                ?.let { RetrofitClient.errorResponse().fromJson(it) }
                            Logger.logError(throwable.stackTraceToString())
                            ClientError(errorModel?.error.toString())
                        }

                        in 500..599 ->
                        {
                            Logger.logError(throwable.stackTraceToString())
                            ServerError("Server error occurred")
                        }
                        else -> {
                            Logger.logError(throwable.stackTraceToString())
                            HttpError("Http error occurred")
                        }
                    }
                }

                is TimeoutException -> {
                    HttpError("Http error occurred")
                    TimeoutError("Can not process task")
                }
                else -> {
                    HttpError("Http error occurred")
                    UnknownError("An unexpected error occurred")
                }
            }
        }
    }

    private object Logger {
        private const val TAG = "ErrorsLog"

        fun logError(error: String) {
            val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val logMessage = "Time: $timestamp Error: $error"
            Log.e(TAG, logMessage)
        }
    }
}
