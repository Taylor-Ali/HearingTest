package com.leaf.remote.di

import android.content.Context
import com.google.gson.Gson
import com.leaf.remote.RemoteDataSource
import com.leaf.remote.RemoteDataSourceImpl
import com.leaf.remote.service.AuditoryTestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @Singleton
    fun providesAuditoryTestServices(retrofit: Retrofit): AuditoryTestService {
        return retrofit.create(AuditoryTestService::class.java)
    }

    @Provides
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    fun providesDispatchers(): Dispatchers {
        return Dispatchers
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(
        @ApplicationContext context: Context,
        gson: Gson,
        dispatchers: Dispatchers,
        auditoryTestService: AuditoryTestService,
    ): RemoteDataSource {
        return RemoteDataSourceImpl(context, dispatchers, gson, auditoryTestService)
    }
}