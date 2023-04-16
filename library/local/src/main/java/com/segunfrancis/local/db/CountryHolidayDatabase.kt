package com.segunfrancis.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.segunfrancis.local.db.entities.CountryLocal

@Database(entities = [CountryLocal::class], version = 2, exportSchema = true)
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
                )
                    .addMigrations(migration_1_2)
                    .build()
            } catch (e: Throwable) {
                null
            }
        }

        private val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE `country` ADD COLUMN `imageUrl` TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}
