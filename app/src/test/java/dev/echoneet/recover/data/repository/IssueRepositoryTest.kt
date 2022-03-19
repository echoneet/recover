package dev.echoneet.recover.data.repository

import dev.echoneet.recover.data.entity.Issue
import dev.echoneet.recover.data.local.IssueDao
import dev.echoneet.recover.data.model.ResultWithStatus
import dev.echoneet.recover.data.remote.IssueRemoteDataSource
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

    val mockIssue = Issue(
        id = 1,
        code = "AA-000001",
        title = "test title",
        description = "test issue",
        status = "Created"
    )

    val mockCancelIssue = Issue(
        id = 2,
        code = "AA-000002",
        title = "test title",
        description = "test issue",
        status = "Cancelled"
    )


    val mockIssueList: List<Issue> = listOf(
        mockIssue,
        mockCancelIssue
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
                mockIssueList,
            )
        )
        `when`(issueDao.getAll()).thenReturn(mockIssueList)

        val issueListFromFunction = issueRepository.getIssueList()

        verify(issueRemoteDataSource).listAllIssue()
        verifyNoMoreInteractions(issueRemoteDataSource)
        verify(issueDao).deleteAll()
        verify(issueDao).insertAll(mockIssueList)
        verify(issueDao).getAll()
        verifyNoMoreInteractions(issueDao)

        assertEquals(
            ResultWithStatus.success(mockIssueList),
            issueListFromFunction
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
        `when`(issueDao.getAll()).thenReturn(mockIssueList)

        val issueListFromFunction = issueRepository.getIssueList()

        verify(issueRemoteDataSource).listAllIssue()
        verifyNoMoreInteractions(issueRemoteDataSource)
        verify(issueDao).getAll()
        verifyNoMoreInteractions(issueDao)

        assertEquals(
            ResultWithStatus.error(mockIssueList, "can not list issue from internet", null),
            issueListFromFunction
        )
    }

    @Test
    fun `insert new issue success`() = runBlocking {
        `when`(issueRemoteDataSource.createNewIssue(mockIssue)).thenReturn(
            ResultWithStatus.success(mockIssue)
        )
        `when`(issueDao.getAll()).thenReturn(mockIssueList)

        val result = issueRepository.createNewIssue(mockIssue)

        verify(issueRemoteDataSource).createNewIssue(mockIssue)
        verifyNoMoreInteractions(issueRemoteDataSource)
        verify(issueDao).insert(mockIssue)
        verify(issueDao).getAll()
        verifyNoMoreInteractions(issueDao)

        assertEquals(ResultWithStatus.success(mockIssueList), result)
    }

    @Test
    fun `insert new issue fail`() = runBlocking {
        `when`(issueRemoteDataSource.createNewIssue(mockIssue)).thenReturn(
            ResultWithStatus.error(null, "error", null)
        )
        `when`(issueDao.getAll()).thenReturn(mockIssueList)

        val result = issueRepository.createNewIssue(mockIssue)

        verify(issueRemoteDataSource).createNewIssue(mockIssue)
        verifyNoMoreInteractions(issueRemoteDataSource)
        verify(issueDao).getAll()
        verifyNoMoreInteractions(issueDao)

        assertEquals(
            ResultWithStatus.error(mockIssueList, "error", null), result
        )
    }


    @Test
    fun `cancel new issue success`() = runBlocking {
        `when`(issueRemoteDataSource.cancelIssue(1)).thenReturn(
            ResultWithStatus.success(mockCancelIssue)
        )
        `when`(issueDao.getAll()).thenReturn(mockIssueList)

        val mockCancelIssueId = 1
        val result = issueRepository.cancelIssue(mockCancelIssueId)

        verify(issueRemoteDataSource).cancelIssue(mockCancelIssueId)
        verifyNoMoreInteractions(issueRemoteDataSource)
        verify(issueDao).update(mockCancelIssue)
        verify(issueDao).getAll()
        verifyNoMoreInteractions(issueDao)

        assertEquals(ResultWithStatus.success(mockIssueList), result)
    }

    @Test
    fun `cancel new issue fail`() = runBlocking {
        `when`(issueRemoteDataSource.cancelIssue(1)).thenReturn(
            ResultWithStatus.error(null, "error", null)
        )
        `when`(issueDao.getAll()).thenReturn(mockIssueList)

        val mockCancelIssueId = 1
        val result = issueRepository.cancelIssue(mockCancelIssueId)

        verify(issueRemoteDataSource).cancelIssue(mockCancelIssueId)
        verifyNoMoreInteractions(issueRemoteDataSource)
        verify(issueDao).getAll()
        verifyNoMoreInteractions(issueDao)

        assertEquals(
            ResultWithStatus.error(mockIssueList, "error", null), result
        )
    }

}