package com.leaf.remote

import android.content.Context
import com.google.gson.Gson
import com.leaf.remote.RemoteDataSource
import com.leaf.remote.helper.RemoteDataService
import com.leaf.remote.helper.Resource
import com.leaf.remote.request.AuditoryTest
import com.leaf.remote.service.AuditoryTestService
import kotlinx.coroutines.Dispatchers

class RemoteDataSourceImpl(
    context: Context,
    dispatcher: Dispatchers,
    gson: Gson,
    private val service: AuditoryTestService,
) : RemoteDataService(context, dispatcher, gson), RemoteDataSource {
    override suspend fun publishAuditoryTest(auditoryTest: AuditoryTest): Resource<Unit> {
        return handleRemoteDataCall { service.publishAuditoryTest(auditoryTest) }
    }
}