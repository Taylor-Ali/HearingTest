package com.leaf.cache

import com.leaf.cache.database.DigitInNoiseDatabase
import com.leaf.cache.database.entities.AuditoryTestEntity

class LocalDataSourceImpl(private val database: DigitInNoiseDatabase) : LocalDataSource {
    override suspend fun getAllAuditoryTests(): List<AuditoryTestEntity>? {
        return database.auditoryTestDao().getAllAuditoryTests()
    }

    override suspend fun addAuditoryTest(auditoryTestEntity: AuditoryTestEntity) {
        database.auditoryTestDao().addAuditoryTest(auditoryTestEntity)
    }

    override suspend fun updateAuditoryTest(auditoryTestEntity: AuditoryTestEntity) {
        database.auditoryTestDao().updateAuditoryTest(auditoryTestEntity)
    }

    override suspend fun deleteAuditoryTest(auditoryTestEntity: AuditoryTestEntity) {
        database.auditoryTestDao().deleteAuditoryTest(auditoryTestEntity)
    }
}