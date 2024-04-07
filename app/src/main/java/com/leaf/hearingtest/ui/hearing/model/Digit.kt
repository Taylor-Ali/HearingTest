package com.leaf.hearingtest.ui.hearing.model

import com.leaf.hearingtest.R

/**
 * Digit
 * Enum used to represent a digit audio clip.
 * @param value represent the string value of the digit played in the audio clip.
 * @param audioResource represent the string path value audio clip.
 */
enum class Digit(val value: String, val audioResource: Int) {
    ONE("1", R.raw.one),
    TWO("2", R.raw.two),
    THREE("3", R.raw.three),
    FOUR("4", R.raw.four),
    FIVE("5", R.raw.five),
    SIX("6", R.raw.six),
    SEVEN("7", R.raw.seven),
    EIGHT("8", R.raw.eight),
    NINE("9", R.raw.nine)
}


