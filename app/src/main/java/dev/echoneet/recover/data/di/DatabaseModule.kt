package dev.echoneet.recover.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.echoneet.recover.data.entity.LocalDatabase
import dev.echoneet.recover.data.local.IssueDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context) : LocalDatabase{
        return Room.databaseBuilder(
            appContext,
            LocalDatabase::class.java,
            "localDatabase.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideIssueDao(localDatabase: LocalDatabase) : IssueDao{
        return localDatabase.issueDao()
    }
}