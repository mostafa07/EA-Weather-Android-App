package com.example.emiratesauctionsweatherapp.data.repository

import android.content.Context
import com.example.emiratesauctionsweatherapp.data.model.domain.WeatherLog
import com.example.emiratesauctionsweatherapp.webservice.CurrentWeatherWebService
import com.example.emiratesauctionsweatherapp.webservice.builder.RetrofitServiceBuilder
import rx.Observable

class WeatherRepository(context: Context) {

    private val mCurrentWeatherWebService: CurrentWeatherWebService =
        RetrofitServiceBuilder.buildService(CurrentWeatherWebService::class.java)

    companion object {

        @Volatile
        private var INSTANCE: WeatherRepository? = null

        fun getInstance(context: Context): WeatherRepository {
            synchronized(lock = this) {
                var instance = INSTANCE
                if (null == instance) {
                    instance = WeatherRepository(context.applicationContext)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    fun getWeatherLogs(
        latitude: Double,
        longitude: Double
    ): Observable<WeatherLog> {
        return mCurrentWeatherWebService.getCurrentWeather(latitude, longitude)
            .map {
                WeatherLog(
                    id = it.id,
                    temperature = it.main.temp,
                    minTemperature = it.main.temp_min,
                    maxTemperature = it.main.temp_max,
                    city = it.name,
                    country = it.sys.country,
                    description = it.weather[0].description,
                    requestDate = it.dt
                )
            }
    }
}