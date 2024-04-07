package com.leaf.repository.di

import com.leaf.cache.LocalDataSource
import com.leaf.remote.RemoteDataSource
import com.leaf.repository.HearingTestRepository
import com.leaf.repository.HearingTestRepositoryImpl
import com.leaf.repository.mapper.HearingTestToAuditoryTestEntityMapper
import com.leaf.repository.mapper.HearingTestToAuditoryTestMapper
import com.leaf.repository.mapper.QuestionnaireAnswersToRoundCacheMapper
import com.leaf.repository.mapper.QuestionnaireAnswersToRoundMapper
import com.leaf.repository.mapper.UserDetailsToUserCacheModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesQuestionnaireToRoundMapper(): QuestionnaireAnswersToRoundMapper {
        return QuestionnaireAnswersToRoundMapper()
    }

    @Provides
    @Singleton
    fun providesQuestionnaireToRoundCacheMapper(): QuestionnaireAnswersToRoundCacheMapper {
        return QuestionnaireAnswersToRoundCacheMapper()
    }

    @Provides
    @Singleton
    fun providesUserDetailsToUserCacheModel(): UserDetailsToUserCacheModel {
        return UserDetailsToUserCacheModel()
    }

    @Provides
    @Singleton
    fun providesHearingTestToAuditoryTestMapper(questionnaireAnswersToRoundMapper: QuestionnaireAnswersToRoundMapper): HearingTestToAuditoryTestMapper {
        return HearingTestToAuditoryTestMapper(questionnaireAnswersToRoundMapper)
    }

    @Provides
    @Singleton
    fun providesHearingTestToAuditoryTestEntityMapper(
        questionnaireAnswersToRoundCacheMapper: QuestionnaireAnswersToRoundCacheMapper,
        userDetailsToUserCacheModel: UserDetailsToUserCacheModel
    ): HearingTestToAuditoryTestEntityMapper {
        return HearingTestToAuditoryTestEntityMapper(
            questionnaireAnswersToRoundCacheMapper,
            userDetailsToUserCacheModel
        )
    }

    @Provides
    @Singleton
    fun providesHearingTestRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        hearingTestToAuditoryTestMapper: HearingTestToAuditoryTestMapper,
        hearingTestToAuditoryTestEntityMapper: HearingTestToAuditoryTestEntityMapper
    ): HearingTestRepository {
        return HearingTestRepositoryImpl(
            remoteDataSource,
            localDataSource,
            hearingTestToAuditoryTestMapper,
            hearingTestToAuditoryTestEntityMapper
        )
    }
}