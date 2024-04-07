package com.leaf.repository

import com.leaf.cache.LocalDataSource
import com.leaf.remote.RemoteDataSource
import com.leaf.remote.helper.Resource
import com.leaf.repository.helper.DataState
import com.leaf.repository.mapper.HearingTestToAuditoryTestEntityMapper
import com.leaf.repository.mapper.HearingTestToAuditoryTestMapper
import com.leaf.repository.model.HearingTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class HearingTestRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val hearingTestToAuditoryTestMapper: HearingTestToAuditoryTestMapper,
    private val hearingTestToAuditoryTestEntityMapper: HearingTestToAuditoryTestEntityMapper
) : HearingTestRepository {
    override suspend fun publishHearingTest(hearingTest: HearingTest): DataState<Unit> {
        val auditoryTest = hearingTestToAuditoryTestMapper.mapFromModel(hearingTest)
        return when (val resource = remoteDataSource.publishAuditoryTest(auditoryTest)) {
            is Resource.Success -> { DataState.Success(resource.code,resource.message,resource.data)}
            is Resource.Error -> {
                DataState.Error(resource.code,resource.message?:"")
            }
            is Resource.Loading -> {DataState.Loading()}
        }
    }

    override suspend fun deleteHearingTest(hearingTest: HearingTest) {
        val auditoryTestEntity = hearingTestToAuditoryTestEntityMapper.mapFromModel(hearingTest)
        localDataSource.deleteAuditoryTest(auditoryTestEntity)
    }

    override suspend fun getAllHearingTests(): List<HearingTest>? {
        return localDataSource.getAllAuditoryTests()?.map {
            hearingTestToAuditoryTestEntityMapper.mapToModel(it)
        }
    }

    override suspend fun storeHearingTest(hearingTest: HearingTest) {
        val auditoryTestEntity = hearingTestToAuditoryTestEntityMapper.mapFromModel(hearingTest)
        localDataSource.addAuditoryTest(auditoryTestEntity)
    }
}