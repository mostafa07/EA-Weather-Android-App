package com.example.emiratesauctionsweatherapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.emiratesauctionsweatherapp.data.model.source.remote.CurrentWeatherApiResponse

@Entity(tableName = "WEATHER_LOGS")
data class WeatherLog(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "TEMP") val temperature: Double,
    @ColumnInfo(name = "MIN_TEMP") val minTemperature: Double,
    @ColumnInfo(name = "MAX_TEMP") val maxTemperature: Double,
    val city: String,
    val country: String,
    val description: String,
    val requestTime: Long
) {

    companion object {
        fun from(currentWeatherApiResponse: CurrentWeatherApiResponse): WeatherLog {
            return WeatherLog(
                id = currentWeatherApiResponse.id,
                temperature = currentWeatherApiResponse.main.temp,
                minTemperature = currentWeatherApiResponse.main.temp_min,
                maxTemperature = currentWeatherApiResponse.main.temp_max,
                city = currentWeatherApiResponse.name,
                country = currentWeatherApiResponse.sys.country,
                description = currentWeatherApiResponse.weather[0].description,
                requestTime = currentWeatherApiResponse.dt
            )
        }
    }
}