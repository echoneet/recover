package dev.echoneet.recover.data.repository

import androidx.room.Dao
import dev.echoneet.recover.data.entity.Issue
import dev.echoneet.recover.data.local.IssueDao
import dev.echoneet.recover.data.model.ResultWithStatus
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class IssueRepositoryTest {
    lateinit var issueRepositoryImpl : IssueRepositoryImpl

    @Mock
    lateinit var issueDao: IssueDao

    var issueList : List<Issue> = listOf(
        Issue(
            id = 1,
            code = "AA-000001",
            title = "test title",
            description = "test issue",
            status = "Created"
        ),
        Issue(
            id = 2,
            code = "AA-000002",
            title = "test title",
            description = "test issue",
            status = "Cancelled"
        )
    )


    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        issueRepositoryImpl = IssueRepositoryImpl(issueDao)
    }

    @Test
    fun listIssue()  = runBlocking {
        `when`(issueDao.getAll()).thenReturn(issueList)

        val issueListFromFunction = issueRepositoryImpl.getIssueList()

        verify(issueDao).getAll()
        verifyNoMoreInteractions(issueDao)

        assertEquals(issueListFromFunction, ResultWithStatus.success(issueList))
    }

}