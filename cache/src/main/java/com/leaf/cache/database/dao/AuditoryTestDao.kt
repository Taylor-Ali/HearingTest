package com.leaf.cache.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.leaf.cache.database.entities.AuditoryTestEntity

/**
 * AuditoryTestDao
 * Data access object for the [AuditoryTestEntity] with basic crud functions.
 */
@Dao
interface AuditoryTestDao {

    /**
     * getAllAuditoryTests
     * gets all AuditoryTests.
     * @return List [AuditoryTestEntity]
     */
    @Query("SELECT * FROM auditory_test ORDER BY id DESC")
    suspend fun getAllAuditoryTests(): List<AuditoryTestEntity>?

    /**
     * addAuditoryTest
     * stores auditoryTestEntity.
     * @param auditoryTestEntity [AuditoryTestEntity]
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAuditoryTest(auditoryTestEntity: AuditoryTestEntity)

    /**
     * updateAuditoryTest
     * updates auditoryTestEntity.
     * @param auditoryTestEntity [AuditoryTestEntity]
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAuditoryTest(auditoryTestEntity: AuditoryTestEntity)

    /**
     * deleteAuditoryTest
     * delete auditoryTestEntity.
     * @param auditoryTestEntity [AuditoryTestEntity]
     */
    @Delete
    suspend fun deleteAuditoryTest(auditoryTestEntity: AuditoryTestEntity)
}