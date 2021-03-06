package dev.echoneet.recover.data.remote

import dev.echoneet.recover.data.entity.Issue
import dev.echoneet.recover.data.helper.RetrofitApiHandleHelper
import dev.echoneet.recover.data.model.ResultWithStatus
import dev.echoneet.recover.data.service.IssueService
import javax.inject.Inject

class IssueRemoteDataSourceImpl @Inject constructor(
    private val issueService: IssueService,
    private val retrofitApiHandleHelper: RetrofitApiHandleHelper
) : IssueRemoteDataSource {
    override suspend fun listAllIssue(): ResultWithStatus<List<Issue>> {
        return retrofitApiHandleHelper.getResponse(
            { issueService.listIssue() },
            "can not list issue from internet"
        )
    }

    override suspend fun createNewIssue(issue: Issue): ResultWithStatus<Issue> {
        return retrofitApiHandleHelper.getResponse(
            { issueService.createIssue(issue) },
            "create failed"
        )
    }

    override suspend fun cancelIssue(issueId: Int): ResultWithStatus<Issue> {
        return retrofitApiHandleHelper.getResponse(
            { issueService.cancelIssue(issueId) },
            "cancel failed"
        )
    }
}