package com.example.emiratesauctionsweatherapp.data.repository

import android.content.Context
import com.example.emiratesauctionsweatherapp.BuildConfig
import com.example.emiratesauctionsweatherapp.data.database.AppDatabase
import com.example.emiratesauctionsweatherapp.data.database.dao.WeatherLogDao
import com.example.emiratesauctionsweatherapp.data.model.WeatherLog
import com.example.emiratesauctionsweatherapp.webservice.CurrentWeatherWebService
import com.example.emiratesauctionsweatherapp.webservice.builder.RetrofitServiceBuilder
import kotlinx.coroutines.flow.Flow
import rx.Observable

class WeatherLogRepository(context: Context) {

    private val mCurrentWeatherWebService: CurrentWeatherWebService =
        RetrofitServiceBuilder.buildService(CurrentWeatherWebService::class.java)

    private val mWeatherLogDao: WeatherLogDao by lazy {
        AppDatabase.getInstance(context.applicationContext).weatherLogDao
    }

    val allWeatherLogs: Flow<List<WeatherLog>> = mWeatherLogDao.getAll()


    fun retrieveWeatherLog(latitude: Number, longitude: Number): Observable<WeatherLog> {
        return mCurrentWeatherWebService.getCurrentWeather(
            latitude = latitude.toInt(),
            longitude = longitude.toInt(),
            units = "metric",
            appId = BuildConfig.OPEN_WEATHER_MAP_API_KEY
        )
            .map {
                val weatherLog = WeatherLog.from(it)
                mWeatherLogDao.insert(weatherLog)
                return@map weatherLog
            }
//            .map {
//                WeatherLog(
//                    id = it.id,
//                    temperature = it.main.temp,
//                    minTemperature = it.main.temp_min,
//                    maxTemperature = it.main.temp_max,
//                    city = it.name,
//                    country = it.sys.country,
//                    description = it.weather[0].description,
//                    requestTime = it.dt
//                )
//            }
    }

    fun saveIntoDatabase(weatherLog: WeatherLog) {
        mWeatherLogDao.insert(weatherLog)
    }


    companion object {

        @Volatile
        private var INSTANCE: WeatherLogRepository? = null

        fun getInstance(context: Context): WeatherLogRepository {
            synchronized(lock = this) {
                var instance = INSTANCE
                if (null == instance) {
                    instance = WeatherLogRepository(context.applicationContext)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}