package com.leaf.repository

import com.leaf.remote.helper.Resource
import com.leaf.repository.helper.DataState
import com.leaf.repository.model.HearingTest
import kotlinx.coroutines.flow.Flow

/**
 * HearingTestRepository
 * repository level functions that would be called in the app module.
 */
interface HearingTestRepository {

    /**
     * publishHearingTest
     * takes hearing test from the app.
     * @param hearingTest
     * @return [Flow] [Resource] [Unit]
     */
    suspend fun publishHearingTest(hearingTest: HearingTest): DataState<Unit>

    /**
     * deleteHearingTest
     * delete hearing test.
     * @param hearingTest
     */
    suspend fun deleteHearingTest(hearingTest: HearingTest)

    /**
     * getAllHearingTests
     * delete hearing test.
     * @return [List] [HearingTest]
     */
    suspend fun getAllHearingTests(): List<HearingTest>?

    /**
     * storeHearingTest
     * store hearing test.
     * @return [List] [HearingTest]
     */
    suspend fun storeHearingTest(hearingTest: HearingTest)

}