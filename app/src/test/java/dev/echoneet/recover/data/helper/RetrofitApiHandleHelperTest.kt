package dev.echoneet.recover.data.helper

import dev.echoneet.recover.data.model.ResultWithStatus
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import retrofit2.Response
import java.io.IOException

class RetrofitApiHandleHelperTest {
    lateinit var retrofitApiHandleHelper: RetrofitApiHandleHelper

    var errorText = "some error text"
    var mockBody = "body"

    @Before
    fun setup() {
        retrofitApiHandleHelper = RetrofitApiHandleHelperImpl()
    }

    @Test
    fun `get result success`() = runBlocking {
        val mockRequest: suspend () -> Response<String> = { Response.success(200, mockBody) }

        val response = retrofitApiHandleHelper.getResponse(mockRequest, errorText)

        assertEquals(response, ResultWithStatus.success(mockBody))
    }

    @Test
    fun `get result when sever 500`() = runBlocking {
        val mockRequest: suspend () -> Response<String> = { Response.success(500, "") }

        val response = retrofitApiHandleHelper.getResponse(mockRequest, errorText)

        assertEquals(response, ResultWithStatus.error(null, errorText, null))
    }


    @Test
    fun `get result when exception`() = runBlocking {
        val mockRequest: suspend () -> Response<String> = { throw IOException("test") }

        val response = retrofitApiHandleHelper.getResponse(mockRequest, errorText)

        assertEquals(response, ResultWithStatus.error(null, errorText, null))
    }
}