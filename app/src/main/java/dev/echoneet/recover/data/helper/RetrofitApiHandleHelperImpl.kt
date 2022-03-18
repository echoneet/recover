package dev.echoneet.recover.data.helper

import dev.echoneet.recover.data.model.ResultWithStatus
import retrofit2.Response

class RetrofitApiHandleHelperImpl : RetrofitApiHandleHelper {
    override suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): ResultWithStatus<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                ResultWithStatus.success(result.body())
            } else {
                ResultWithStatus.error(null, defaultErrorMessage, null)
            }
        } catch (e: Throwable) {
            ResultWithStatus.error(null, defaultErrorMessage, null)
        }
    }
}