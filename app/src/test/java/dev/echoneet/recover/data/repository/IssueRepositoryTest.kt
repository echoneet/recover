package dev.echoneet.recover.data.repository

import androidx.room.Dao
import dev.echoneet.recover.data.entity.Issue
import dev.echoneet.recover.data.local.IssueDao
import dev.echoneet.recover.data.model.ResultWithStatus
import dev.echoneet.recover.data.remote.IssueRemoteDataSource
import dev.echoneet.recover.data.remote.IssueRemoteDataSourceImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class IssueRepositoryTest {
    lateinit var issueRepository: IssueRepository

    @Mock
    lateinit var issueDao: IssueDao

    @Mock
    lateinit var issueRemoteDataSource: IssueRemoteDataSource

    var issueList: List<Issue> = listOf(
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
    fun setup() {
        MockitoAnnotations.openMocks(this)
        issueRepository = IssueRepositoryImpl(issueDao, issueRemoteDataSource)
    }

    @Test
    fun `List issue while online`() = runBlocking {
        `when`(issueRemoteDataSource.listAllIssue()).thenReturn(
            ResultWithStatus.success(
                issueList,
            )
        )
        `when`(issueDao.getAll()).thenReturn(issueList)

        val issueListFromFunction = issueRepository.getIssueList()

        verify(issueRemoteDataSource).listAllIssue()
        verifyNoMoreInteractions(issueRemoteDataSource)
        verify(issueDao).deleteAll()
        verify(issueDao).insertAll(issueList)
        verify(issueDao).getAll()
        verifyNoMoreInteractions(issueDao)

        assertEquals(
            issueListFromFunction,
            ResultWithStatus.success(issueList)
        )
    }

    @Test
    fun `List issue while offline`() = runBlocking {
        `when`(issueRemoteDataSource.listAllIssue()).thenReturn(
            ResultWithStatus.error(
                null,
                "can not list issue from internet",
                null
            )
        )
        `when`(issueDao.getAll()).thenReturn(issueList)

        val issueListFromFunction = issueRepository.getIssueList()

        verify(issueRemoteDataSource).listAllIssue()
        verifyNoMoreInteractions(issueRemoteDataSource)
        verify(issueDao).getAll()
        verifyNoMoreInteractions(issueDao)

        assertEquals(
            issueListFromFunction,
            ResultWithStatus.error(issueList, "can not list issue from internet", null)
        )
    }

}