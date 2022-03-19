package dev.echoneet.recover.data.remote

import dev.echoneet.recover.data.entity.Issue
import dev.echoneet.recover.data.model.ResultWithStatus

interface IssueRemoteDataSource {
    suspend fun listAllIssue(): ResultWithStatus<List<Issue>>

    suspend fun createNewIssue(issue: Issue): ResultWithStatus<Issue>
}