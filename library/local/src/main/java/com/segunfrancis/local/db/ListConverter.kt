package com.segunfrancis.local.db

import androidx.room.TypeConverter
import com.segunfrancis.local.db.entities.CountryLocal
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ListConverter {

    private val adapter by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val list = Types.newParameterizedType(List::class.java, CountryLocal::class.java)
        return@lazy moshi.adapter<List<CountryLocal>>(list)
    }

    @TypeConverter
    fun toJson(categories: List<CountryLocal>): String {
        return adapter.toJson(categories)
    }

    @TypeConverter
    fun fromJson(json: String) : List<CountryLocal>? {
        return adapter.fromJson(json)
    }
}
