package dev.echoneet.recover.data.repository

import dev.echoneet.recover.data.entity.Issue
import dev.echoneet.recover.data.local.IssueDao
import dev.echoneet.recover.data.model.ResultWithStatus
import javax.inject.Inject

class IssueRepositoryImpl @Inject constructor(
    private val issueDao: IssueDao
) : IssueRepository {
    override suspend fun getIssueList(): ResultWithStatus<List<Issue>> {
        return ResultWithStatus.success(issueDao.getAll())
    }

}