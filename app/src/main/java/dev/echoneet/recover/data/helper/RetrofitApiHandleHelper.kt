package dev.echoneet.recover.data.helper

import dev.echoneet.recover.data.model.ResultWithStatus
import retrofit2.Response

interface RetrofitApiHandleHelper {
    suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): ResultWithStatus<T>
}