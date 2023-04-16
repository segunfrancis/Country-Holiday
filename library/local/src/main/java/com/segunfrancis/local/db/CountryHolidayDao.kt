package com.segunfrancis.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.segunfrancis.local.db.entities.CountryLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryHolidayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCountries(vararg country: CountryLocal)

    @Query("SELECT * FROM country")
    fun getCountries(): List<CountryLocal>
}
