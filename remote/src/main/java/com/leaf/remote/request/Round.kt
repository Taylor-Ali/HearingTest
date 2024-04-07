package com.leaf.remote.request

import com.google.gson.annotations.SerializedName

/**
 * Round
 * Request class used to post the Questionnaire data collect by the app to the endpoint
 * @param difficulty [Int] represents the noise difficultly presented from 1-9.
 * @param tripletPlayed [String] represents string of digits played during the hearing test.
 * @param tripletAnswered [String] represents string of digits the use responded with during the hearing test.
 */
data class Round(
    @SerializedName("difficulty")
    val difficulty: Int,
    @SerializedName("triplet_played")
    val tripletPlayed: String,
    @SerializedName("triplet_answered")
    val tripletAnswered: String
)
