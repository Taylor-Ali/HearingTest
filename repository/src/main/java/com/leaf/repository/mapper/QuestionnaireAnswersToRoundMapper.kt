package com.leaf.repository.mapper

import com.leaf.remote.request.Round
import com.leaf.repository.model.QuestionnaireAnswers


/**
 * QuestionnaireToRoundCacheMapper
 * Mapping class that maps [QuestionnaireAnswers] to the [Round] Remote model.
 */
class QuestionnaireAnswersToRoundMapper : Mapper<Round, QuestionnaireAnswers> {
    override fun mapFromModel(type: QuestionnaireAnswers): Round {
        return Round(
            difficulty = type.difficulty,
            tripletPlayed = type.tripletPlayed,
            tripletAnswered = type.tripletAnswered
        )
    }

    override fun mapToModel(type: Round): QuestionnaireAnswers {
        return QuestionnaireAnswers(
            difficulty = type.difficulty,
            tripletPlayed = type.tripletPlayed,
            tripletAnswered = type.tripletAnswered
        )
    }
}