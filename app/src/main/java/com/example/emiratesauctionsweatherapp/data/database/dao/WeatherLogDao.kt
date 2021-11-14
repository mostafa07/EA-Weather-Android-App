package com.example.emiratesauctionsweatherapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.emiratesauctionsweatherapp.data.model.WeatherLog
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherLogDao {

    @Insert
    fun insert(weatherLog: WeatherLog)

    @Insert
    fun insertAll(vararg weatherLogs: WeatherLog)

    @Query("SELECT * FROM WEATHER_LOGS")
    fun getAll(): Flow<List<WeatherLog>>
}