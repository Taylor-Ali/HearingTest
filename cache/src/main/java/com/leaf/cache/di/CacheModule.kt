package com.leaf.cache.di

import android.content.Context
import com.leaf.cache.LocalDataSource
import com.leaf.cache.LocalDataSourceImpl
import com.leaf.cache.database.DigitInNoiseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {


    @Provides
    @Singleton
    fun providesDatabaseInstance(@ApplicationContext appContext: Context): DigitInNoiseDatabase {
        return DigitInNoiseDatabase.getInstance(appContext)
    }

    @Provides
    @Singleton
    fun providesCacheDataSource(
        database: DigitInNoiseDatabase,
    ): LocalDataSource {
        return LocalDataSourceImpl(database)
    }
}