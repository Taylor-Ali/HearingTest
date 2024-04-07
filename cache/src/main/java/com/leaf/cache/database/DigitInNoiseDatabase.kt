package com.leaf.cache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leaf.cache.database.converter.Converters
import com.leaf.cache.database.dao.AuditoryTestDao
import com.leaf.cache.database.entities.AuditoryTestEntity

@Database(
    entities = [AuditoryTestEntity::class], version = 1
)
@TypeConverters(Converters::class)
abstract class DigitInNoiseDatabase : RoomDatabase() {
    abstract fun auditoryTestDao(): AuditoryTestDao

    companion object {
        private var instance: DigitInNoiseDatabase? = null
        private const val DB_NAME = "digit_in_noise_database"

        @Synchronized
        fun getInstance(ctx: Context): DigitInNoiseDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, DigitInNoiseDatabase::class.java,
                    DB_NAME
                )
                    .addTypeConverter(Converters())
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }
    }
}