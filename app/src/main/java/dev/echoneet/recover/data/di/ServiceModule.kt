package dev.echoneet.recover.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.echoneet.recover.data.helper.RetrofitApiHandleHelper
import dev.echoneet.recover.data.remote.IssueRemoteDataSource
import dev.echoneet.recover.data.remote.IssueRemoteDataSourceImpl
import dev.echoneet.recover.data.service.IssueService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    private val issueApiUrl = "http://localhost:5108/"

    @Provides
    @Named("issue")
    fun provideIssueApiRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(issueApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideIssueService(@Named("issue") retrofit: Retrofit): IssueService {
        return retrofit.create(IssueService::class.java)
    }

    @Provides
    fun provideIssueRemoteDataSource(issueService: IssueService,retrofitApiHandleHelper: RetrofitApiHandleHelper) : IssueRemoteDataSource{
        return IssueRemoteDataSourceImpl(issueService, retrofitApiHandleHelper)
    }
}