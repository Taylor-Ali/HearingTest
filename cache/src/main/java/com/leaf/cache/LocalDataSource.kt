package com.leaf.cache

import com.leaf.cache.database.entities.AuditoryTestEntity

/**
 * LocalDataSource
 * Wrappers database and dao functions.
 */
interface LocalDataSource {
    /**
     * getAllAuditoryTests
     * returns all auditory tests.
     * @return List of [AuditoryTestEntity]
     */
    suspend fun getAllAuditoryTests(): List<AuditoryTestEntity>?

    /**
     * addAuditoryTest
     * stores auditory test.
     * @param auditoryTestEntity [AuditoryTestEntity]
     */
    suspend fun addAuditoryTest(auditoryTestEntity: AuditoryTestEntity)

    /**
     * updateAuditoryTest
     * updates auditory test.
     * @param auditoryTestEntity [AuditoryTestEntity]
     */
    suspend fun updateAuditoryTest(auditoryTestEntity: AuditoryTestEntity)

    /**
     * deleteAuditoryTest
     * deletes auditory test.
     * @param auditoryTestEntity [AuditoryTestEntity]
     */
    suspend fun deleteAuditoryTest(auditoryTestEntity: AuditoryTestEntity)
}