package dev.echoneet.recover.data.repository

import dev.echoneet.recover.data.entity.Issue
import dev.echoneet.recover.data.local.IssueDao
import dev.echoneet.recover.data.model.ResultWithStatus

interface IssueRepository {
    suspend fun getIssueList(): ResultWithStatus<List<Issue>>

    suspend fun createNewIssue(issue: Issue): ResultWithStatus<List<Issue>>

    suspend fun cancelIssue(issueId: Int): ResultWithStatus<List<Issue>>
}