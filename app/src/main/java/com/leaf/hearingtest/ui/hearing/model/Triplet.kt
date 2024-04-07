package com.leaf.hearingtest.ui.hearing.model


/**
 * Triplet
 * Combination of audio clips or sounds represents a triplet
 * @param tripletValue represent the value of all digits played e.g 123.
 * @param digitList represent the audio clips combined.
 */
data class Triplet(
    val tripletValue: String,
    val digitList: List<Digit>
)

/**
 * createTriplet
 * Creates a [Triplet] combination by select three random digits.
 * @return [Triplet]
 */
fun createTriplet(): Triplet {
    val firstDigit = Digit.entries.random()
    val secondDigit = Digit.entries.random()
    val thirdDigit = Digit.entries.random()


    return Triplet(
        tripletValue = "${firstDigit.value}${secondDigit.value}${thirdDigit.value}",
        digitList = listOf(firstDigit, secondDigit, thirdDigit)
    )
}