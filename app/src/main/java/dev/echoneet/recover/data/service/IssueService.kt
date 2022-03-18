package dev.echoneet.recover.data.service

import dev.echoneet.recover.data.entity.Issue
import retrofit2.Response
import retrofit2.http.GET

interface IssueService {
    @GET("issue")
    suspend fun listIssue(): Response<List<Issue>>
}