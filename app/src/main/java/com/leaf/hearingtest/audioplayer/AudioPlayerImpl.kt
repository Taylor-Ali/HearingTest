package com.leaf.hearingtest.audioplayer

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import timber.log.Timber

class AudioPlayerImpl(private val context: Context, private var mediaPlayer: MediaPlayer) :
    AudioPlayer, MediaPlayer.OnCompletionListener {
    private val isPlayingChannel = Channel<Boolean>()
    private var isPlaying = false

    override suspend fun init() {
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
        mediaPlayer.setOnCompletionListener(this)

    }

    override suspend fun loadAndPlay(audioFile: Uri) {
        try {
            mediaPlayer.setDataSource(context, audioFile)
            mediaPlayer.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun loadAndPlay(audioResource: Int) {
        try {
            mediaPlayer = MediaPlayer.create(context, audioResource)
            mediaPlayer.start()
            Timber.v("mediaPlayer.duration"+mediaPlayer.duration)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun play() {
        try {
            mediaPlayer.start()
            isPlaying = true
            isPlayingChannel.send(isPlaying)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun pause() {
        try {
            mediaPlayer.pause()
            isPlaying = false
            isPlayingChannel.send(isPlaying)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun stop() {
        try {
            mediaPlayer.stop()
            isPlaying = false
            isPlayingChannel.send(isPlaying)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun isPlaying(): Flow<Boolean> {
        return isPlayingChannel.consumeAsFlow()
    }

    override fun reset() {
        try {
            mediaPlayer.reset()
            mediaPlayer.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getDuration(): Int? {
        return try {
            mediaPlayer.duration
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun destroy() {
        try {
            mediaPlayer.release()
            isPlayingChannel.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        try {
            mp?.stop()
            mp?.release()
            isPlaying = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}