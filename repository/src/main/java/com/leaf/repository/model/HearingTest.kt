package com.leaf.repository.model

/**
 * HearingTest
 * repository model that represents the hearing test.
 * @param id [Long]
 * @param score [Int] represents score on test.
 * @param questionnaireAnswers List[QuestionnaireAnswers] represents the questionnaire.
 * @param userDetails [UserDetails] represents the user details captured.
 */
data class HearingTest(
    val id: Long? = 0,
    val score: Int,
    val questionnaireAnswers: List<QuestionnaireAnswers>,
    val userDetails: UserDetails? = null
)
