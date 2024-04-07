package com.leaf.remote.di

import android.content.Context
import com.leaf.remote.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @Named("HttpConnectTimeout")
    fun providesHttpConnectTimeout(@ApplicationContext applicationContext: Context): Int {
        return applicationContext.resources
            .getInteger(R.integer.http_connect_timeout_in_seconds)
    }

    @Provides
    @Singleton
    @Named("HttpReadTimeout")
    fun providesHttpReadTimeout(@ApplicationContext applicationContext: Context): Int {
        return applicationContext.resources
            .getInteger(R.integer.http_read_timeout_in_seconds)
    }

    @Provides
    @Singleton
    @Named("HttpWriteTimeout")
    fun providesHttpWriteTimeout(@ApplicationContext applicationContext: Context): Int {
        return applicationContext.resources
            .getInteger(R.integer.http_write_timeout_in_seconds)
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }


    @Provides
    @Singleton
    fun providesHttpClient(
        @ApplicationContext applicationContext: Context,
        httpLoggingInterceptor: Interceptor,
        @Named("HttpConnectTimeout") connectTimeout: Int,
        @Named("HttpReadTimeout") readTimeout: Int,
        @Named("HttpWriteTimeout") writeTimeout: Int
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
            .writeTimeout(writeTimeout.toLong(), TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://enoqczf2j2pbadx.m.pipedream.net/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}