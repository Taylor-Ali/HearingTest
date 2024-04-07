package com.leaf.remote

import com.leaf.remote.helper.Resource
import com.leaf.remote.request.AuditoryTest

/**
 * RemoteDataSource
 * Class used to make the api call to send the test to the api
 */
interface RemoteDataSource {
    /**
     * publishAuditoryTest
     * to send the test to the api.
     * @param auditoryTest
     * @return Resource<Unit>
     */
    suspend fun publishAuditoryTest(auditoryTest: AuditoryTest): Resource<Unit>
}