package dev.echoneet.recover.data.repository

import dev.echoneet.recover.data.entity.Issue
import dev.echoneet.recover.data.local.IssueDao
import dev.echoneet.recover.data.model.ResultWithStatus
import dev.echoneet.recover.data.remote.IssueRemoteDataSource
import javax.inject.Inject

class IssueRepositoryImpl @Inject constructor(
    private val issueDao: IssueDao,
    private val issueRemoteDataSource: IssueRemoteDataSource
) : IssueRepository {
    override suspend fun getIssueList(): ResultWithStatus<List<Issue>> {
        val result = issueRemoteDataSource.listAllIssue()
        if (result.status == ResultWithStatus.Status.ERROR) {
            return ResultWithStatus.error(issueDao.getAll(), result.message, result.error)
        }

        issueDao.deleteAll()
        result.data?.let { issueDao.insertAll(it) }

        return ResultWithStatus.success(issueDao.getAll())
    }

    override suspend fun createNewIssue(issue: Issue): ResultWithStatus<List<Issue>> {
        val result = issueRemoteDataSource.createNewIssue(issue)
        if (result.status == ResultWithStatus.Status.ERROR || result.data == null) {
            return ResultWithStatus.error(issueDao.getAll(), result.message, result.error)
        }

        issueDao.insert(result.data)
        return ResultWithStatus.success(issueDao.getAll())


    }

    override suspend fun cancelIssue(issueId: Int): ResultWithStatus<List<Issue>> {
        val result = issueRemoteDataSource.cancelIssue(issueId)
        if (result.status == ResultWithStatus.Status.ERROR || result.data == null) {
            return ResultWithStatus.error(issueDao.getAll(), result.message, result.error)
        }

        issueDao.update(result.data)
        return ResultWithStatus.success(issueDao.getAll())
    }

}