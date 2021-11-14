package com.example.emiratesauctionsweatherapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.emiratesauctionsweatherapp.data.database.dao.WeatherLogDao
import com.example.emiratesauctionsweatherapp.data.model.WeatherLog

@Database(
    entities = [WeatherLog::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val weatherLogDao: WeatherLogDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock = this) {
                var instance = INSTANCE
                if (null == instance) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}