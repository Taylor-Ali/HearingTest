package com.leaf.repository.mapper

import com.leaf.cache.database.model.RoundCacheModel
import com.leaf.repository.model.QuestionnaireAnswers

/**
 * QuestionnaireAnswersToRoundCacheMapper
 * Mapping class that maps [QuestionnaireAnswers] to the [RoundCache] Database model.
 */
class QuestionnaireAnswersToRoundCacheMapper:Mapper<RoundCacheModel,QuestionnaireAnswers> {
    override fun mapFromModel(type: QuestionnaireAnswers): RoundCacheModel {
        return RoundCacheModel(
            difficulty = type.difficulty,
            tripletPlayed = type.tripletPlayed,
            tripletAnswered = type.tripletAnswered
        )
    }

    override fun mapToModel(type: RoundCacheModel): QuestionnaireAnswers {
        return QuestionnaireAnswers(
            difficulty = type.difficulty,
            tripletPlayed = type.tripletPlayed,
            tripletAnswered = type.tripletAnswered
        )
    }
}