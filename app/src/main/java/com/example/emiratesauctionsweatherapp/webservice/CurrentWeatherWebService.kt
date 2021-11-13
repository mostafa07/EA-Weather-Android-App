package com.example.emiratesauctionsweatherapp.webservice

import com.example.emiratesauctionsweatherapp.data.model.source.remote.CurrentWeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface CurrentWeatherWebService {

    @GET(CURRENT_WEATHER_END_POINT)
    fun getCurrentWeather(
        @Query(LATITUDE_QUERY_PARAM) latitude: Number,
        @Query(LONGITUDE_QUERY_PARAM) longitude: Number,
        @Query(APP_ID_QUERY_PARAM) appId: String,
    ): Observable<CurrentWeatherApiResponse>

    companion object {
        const val CURRENT_WEATHER_END_POINT = "weather"

        const val APP_ID_QUERY_PARAM = "appid"
        const val LATITUDE_QUERY_PARAM = "lat"
        const val LONGITUDE_QUERY_PARAM = "lon"
    }
}