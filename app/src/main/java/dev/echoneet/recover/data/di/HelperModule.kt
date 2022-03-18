package dev.echoneet.recover.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.echoneet.recover.data.helper.RetrofitApiHandleHelper
import dev.echoneet.recover.data.helper.RetrofitApiHandleHelperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HelperModule {
    @Singleton
    @Provides
    public fun provideRetrofitApiHandleHelper() : RetrofitApiHandleHelper{
        return RetrofitApiHandleHelperImpl()
    }
}