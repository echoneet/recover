package dev.echoneet.recover.data.repository

import dev.echoneet.recover.data.entity.Issue
import dev.echoneet.recover.data.local.IssueDao
import dev.echoneet.recover.data.model.ResultWithStatus

interface IssueRepository {
    suspend fun getIssueList() : ResultWithStatus<List<Issue>>
}