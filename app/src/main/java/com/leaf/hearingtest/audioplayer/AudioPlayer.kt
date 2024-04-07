package com.leaf.hearingtest.audioplayer

import android.net.Uri
import kotlinx.coroutines.flow.Flow
/**
 * AudioPlayer
 * Wrapper that wraps around the android media player api and exposes functions to listen to audio.
 */
interface AudioPlayer {
    /**
     * init
     * initialises media play object with audio configurations.
     */
    suspend fun init()

    /**
     * loadAudioFile
     * loads and prepares the audio file.
     * @param audioFile [Uri] audio file to be played.
     */
    suspend fun loadAndPlay(audioFile: Uri)

    /**
     * loadAudioFile
     * loads and plays the audio file.
     * @param audioResource [Int] audio file to be played.
     */
    suspend fun loadAndPlay(audioResource: Int)

    /**
     * play
     * plays audio file.
     */
    suspend fun play()

    /**
     * play
     * pauses the audio file.
     */
    suspend fun pause()

    /**
     * stop
     * stops the audio file.
     */
    suspend fun stop()
    /**
     * isPlaying
     * flag that updates if the audio state goes from playing to not playing.
     * @return Boolean
     */
    suspend fun isPlaying(): Flow<Boolean>
    /**
     * reset
     * clears the audio data source.
     */
     fun reset()

    /**
     * getDuration
     * returns total duration of audio file.
     */
    suspend fun getDuration(): Int?
    /**
     * destroy
     * destroys media player object.
     */
     fun destroy()
}