package com.leaf.remote.service

import com.leaf.remote.request.AuditoryTest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuditoryTestService {
    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun publishAuditoryTest(@Body auditoryTest: AuditoryTest): Response<Unit>
}