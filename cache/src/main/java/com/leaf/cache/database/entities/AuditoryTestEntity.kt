package com.leaf.cache.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leaf.cache.database.model.RoundCacheModel
import com.leaf.cache.database.model.UserCacheModel

/**
 * AuditoryTestEntity
 * Represents object that will be saved to the auditory_test table .
 * @param id [Long] primary key.
 * @param score [Int] score on test.
 * @param rounds List [RoundCacheModel] represents the test questionnaire.
 * @param user [UserCacheModel] embedded object that represents user information associated with the test.
 */
@Entity(tableName = "auditory_test")
data class AuditoryTestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "rounds") val rounds: List<RoundCacheModel>,
    @Embedded val user: UserCacheModel,
)
