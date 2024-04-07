package com.leaf.cache.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leaf.cache.database.model.RoundCacheModel

/**
 * Converters
 * uses to convert types not supported by room database into types that it does
 */
@ProvidedTypeConverter
class Converters {
    private val gson = Gson()

    /**
     * fromRoundsToJSON
     * converts list of rounds to string before storing it in the database.
     */
    @TypeConverter
    fun fromRoundsToJSON(rounds: List<RoundCacheModel>): String {
        return gson.toJson(rounds).toString()
    }

    /**
     * fromRoundsToJSON
     * converts string of rounds to list when the entity object property rounds is called.
     */
    @TypeConverter
    fun toRoundFromJSON(value: String): List<RoundCacheModel> {
        val typeToken = object : TypeToken<List<RoundCacheModel>>() {}.type
        return gson.fromJson(value,typeToken)
    }
}