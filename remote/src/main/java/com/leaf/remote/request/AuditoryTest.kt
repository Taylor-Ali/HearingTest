package com.leaf.remote.request

import com.google.gson.annotations.SerializedName

/**
 * AuditoryTest
 * Request class used to post the AuditoryTest data collect by the app to the endpoint
 * @param score [Int] represents the initial rate the base currency was at.
 * @param rounds List [Round] represents the hearing test.
 */
data class AuditoryTest(
    @SerializedName("score")
    val score: Int,
    @SerializedName("rounds")
    val rounds: List<Round>,
)