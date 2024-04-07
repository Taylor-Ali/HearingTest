package com.leaf.repository.model

/**
 * QuestionnaireAnswers
 * @param difficulty [Int] represents the noise difficultly presented from 1-9.
 * @param tripletPlayed [String] represents string of digits played during the hearing test.
 * @param tripletAnswered [String] represents string of digits the use responded with during the hearing test.
 */
data class QuestionnaireAnswers(
    val difficulty: Int,
    val tripletPlayed: String,
    val tripletAnswered: String
)