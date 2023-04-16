package com.segunfrancis.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.segunfrancis.local.db.entities.CountryLocal

@Database(entities = [CountryLocal::class], version = 1, exportSchema = true)
@TypeConverters(ListConverter::class)
abstract class CountryHolidayDatabase : RoomDatabase() {

    abstract fun getDao(): CountryHolidayDao

    companion object {
        fun getDatabase(context: Context): CountryHolidayDatabase? {
            return try {
                Room.databaseBuilder(
                    context,
                    CountryHolidayDatabase::class.java,
                    "country_holiday_database"
                ).build()
            } catch (e: Throwable) {
                null
            }
        }
    }
}
