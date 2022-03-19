package dev.echoneet.recover.data.service

import dev.echoneet.recover.data.entity.Issue
import retrofit2.Response
import retrofit2.http.*

interface IssueService {
    @GET("issue")
    suspend fun listIssue(): Response<List<Issue>>

    @POST("issue")
    suspend fun createIssue(@Body issue: Issue): Response<Issue>

    @PUT("issue/{issueId}/cancel")
    suspend fun cancelIssue(@Path("issueId") issueId: Int): Response<Issue>
}