package com.tibi.treetable.data.database.model

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class TreeConverter {
    @TypeConverter
    fun fromDiameters(diameters: List<Int>): String {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(List::class.java, Int::class.javaObjectType)
        val jsonAdapter = moshi.adapter<List<Int>>(type)

        return jsonAdapter.toJson(diameters)
    }

    @TypeConverter
    fun toDiameters(string: String): List<Int> {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(List::class.java, Int::class.javaObjectType)
        val jsonAdapter = moshi.adapter<List<Int>>(type)

        return jsonAdapter.fromJson(string) ?: emptyList()
    }
}