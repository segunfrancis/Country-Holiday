package com.segunfrancis.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class CountryLocal(
    @PrimaryKey
    val code: String,
    val name: String,
    val imageUrl: String
)
