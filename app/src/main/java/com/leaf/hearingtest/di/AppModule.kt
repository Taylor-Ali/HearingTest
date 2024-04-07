package com.leaf.hearingtest.di

import android.media.MediaPlayer
import com.leaf.hearingtest.mapper.UserModelToUserDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesMediaPlayer(): MediaPlayer {
        return MediaPlayer()
    }

    @Provides
    fun providesUserModelMapper(): UserModelToUserDetails {
        return UserModelToUserDetails()
    }
}