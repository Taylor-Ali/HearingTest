package com.leaf.hearingtest.ui.hearing.model

/**
 * Question
 * The Question data class represents one instance of the hearing test or one round
 * @param id represent the noise level as difficulty played in the audio clip.
 * @param noise [Noise] enum.
 * @param triplet
 * @param pointValue each question represents a point.
 */
data class Question(
    val id : Int,
    val noise: Noise,
    val triplet: Triplet,
    val pointValue: Int,
)


fun createQuestionnaire(): List<Question> {
    return listOf(
        Question(
            id = 1,
            noise = Noise.NOISE_ONE,
            triplet = createTriplet(),
            pointValue = 1
        ),
        Question(
            id = 2,
            noise = Noise.NOISE_TWO,
            triplet = createTriplet(),
            pointValue = 2
        ),
        Question(
            id = 3,
            noise = Noise.NOISE_THREE,
            triplet = createTriplet(),
            pointValue = 3
        ),
        Question(
            id = 4,
            noise = Noise.NOISE_FOUR,
            triplet = createTriplet(),
            pointValue = 4
        ),
        Question(
            id = 5,
            noise = Noise.NOISE_FIVE,
            triplet = createTriplet(),
            pointValue = 5
        ),
        Question(
            id = 6,
            noise = Noise.NOISE_SIX,
            triplet = createTriplet(),
            pointValue = 6
        ),
        Question(
            id = 7,
            noise = Noise.NOISE_SEVEN,
            triplet = createTriplet(),
            pointValue = 7
        ),
        Question(
            id = 8,
            noise = Noise.NOISE_EIGHT,
            triplet = createTriplet(),
            pointValue = 8
        ),
        Question(
            id = 9,
            noise = Noise.NOISE_NINE,
            triplet = createTriplet(),
            pointValue = 9
        ),
        Question(
            id = 10,
            noise = Noise.NOISE_TEN,
            triplet = createTriplet(),
            pointValue = 10
        )
    )
}