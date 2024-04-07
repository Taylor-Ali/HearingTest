package com.leaf.repository.mapper

import com.leaf.cache.database.entities.AuditoryTestEntity
import com.leaf.repository.model.HearingTest

/**
 * HearingTestToAuditoryTestEntityMapper
 * Mapping class that maps Hearing Tests to the AuditoryTestEntity.
 * @param questionnaireAnswersToRoundCacheMapper is another mapper which maps the questionnaire to its database model.
 * @param userDetailsToUserCacheModel is another mapper which maps the [UserDetails] to its database model.
 */
class HearingTestToAuditoryTestEntityMapper(
    private val questionnaireAnswersToRoundCacheMapper: QuestionnaireAnswersToRoundCacheMapper,
    private val userDetailsToUserCacheModel: UserDetailsToUserCacheModel
) : Mapper<AuditoryTestEntity, HearingTest> {
    override fun mapFromModel(type: HearingTest): AuditoryTestEntity {
        return AuditoryTestEntity(
            score = type.score,
            rounds = type.questionnaireAnswers.map {
                questionnaireAnswersToRoundCacheMapper.mapFromModel(it)
            },
            user = type.userDetails.let { userDetails ->
                userDetailsToUserCacheModel.mapFromModel(userDetails!!)
            })
    }

    override fun mapToModel(type: AuditoryTestEntity): HearingTest {
        return HearingTest(id = type.id, score = type.score, questionnaireAnswers = type.rounds.map {
            questionnaireAnswersToRoundCacheMapper.mapToModel(it)
        }, userDetails = type.user.let { userDetails ->
            userDetailsToUserCacheModel.mapToModel(
                userDetails
            )
        })
    }
}