package com.leaf.hearingtest.ui.hearing.model

import com.leaf.hearingtest.R

/**
 * Noise
 * Enum used to represent a digit audio clip.
 * @param difficulty represent the noise level as difficulty played in the audio clip.
 * @param audioResource represent the string path value audio clip.
 */
enum class Noise(val difficulty: Int, val audioResource: Int) {
    NOISE_ONE(1, R.raw.noise_1),
    NOISE_TWO(2, R.raw.noise_2),
    NOISE_THREE(3, R.raw.noise_3),
    NOISE_FOUR(4, R.raw.noise_4),
    NOISE_FIVE(5, R.raw.noise_5),
    NOISE_SIX(6, R.raw.noise_6),
    NOISE_SEVEN(7, R.raw.noise_7),
    NOISE_EIGHT(8, R.raw.noise_8),
    NOISE_NINE(9, R.raw.noise_9),
    NOISE_TEN(10, R.raw.noise_10),
}
