package com.leaf.repository.mapper

import com.leaf.remote.request.AuditoryTest
import com.leaf.repository.model.HearingTest

/**
 * HearingTestToAuditoryTestMapper
 * Mapping class that maps Hearing Tests to the AuditoryTest Remote Model.
 * @param questionnaireAnswersToRoundMapper is another mapper which maps the questionnaire to its remote data model.
 */
class HearingTestToAuditoryTestMapper(private val questionnaireAnswersToRoundMapper: QuestionnaireAnswersToRoundMapper) :
    Mapper<AuditoryTest, HearingTest> {
    override fun mapFromModel(type: HearingTest): AuditoryTest {
        return AuditoryTest(score = type.score, rounds = type.questionnaireAnswers.map {
            questionnaireAnswersToRoundMapper.mapFromModel(it)
        })
    }

    override fun mapToModel(type: AuditoryTest): HearingTest {
        return HearingTest(score = type.score, questionnaireAnswers = type.rounds.map {
            questionnaireAnswersToRoundMapper.mapToModel(it)
        }, userDetails = null)
    }
}