package com.leaf.audioplayer.di

import android.content.Context
import android.media.MediaPlayer
import com.leaf.hearingtest.audioplayer.AudioPlayer
import com.leaf.hearingtest.audioplayer.AudioPlayerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AudioPlayerModule {

    @Provides
    fun providesMediaPlayer(): MediaPlayer {
        return MediaPlayer()
    }

    @Provides
    fun provideAudioPlayer(
        @ApplicationContext context: Context,
        mediaPlayer: MediaPlayer
    ): com.leaf.hearingtest.audioplayer.AudioPlayer {
        return com.leaf.hearingtest.audioplayer.AudioPlayerImpl(context, mediaPlayer)
    }
}