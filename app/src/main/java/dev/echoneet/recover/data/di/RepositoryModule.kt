package dev.echoneet.recover.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.echoneet.recover.data.local.IssueDao
import dev.echoneet.recover.data.remote.IssueRemoteDataSource
import dev.echoneet.recover.data.repository.IssueRepository
import dev.echoneet.recover.data.repository.IssueRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    public fun provideIssueRepository(
        issueDao: IssueDao,
        issueRemoteDataSource: IssueRemoteDataSource
    ): IssueRepository {
        return IssueRepositoryImpl(issueDao, issueRemoteDataSource)
    }
}