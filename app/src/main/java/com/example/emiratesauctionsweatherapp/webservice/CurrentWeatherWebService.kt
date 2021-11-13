package com.example.emiratesauctionsweatherapp.webservice

import com.example.emiratesauctionsweatherapp.data.model.source.remote.CurrentWeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface CurrentWeatherWebService {

    @GET(CURRENT_WEATHER_END_POINT)
    fun getCurrentWeather(
        @Query(LATITUDE_QUERY_PARAM) latitude: Double,
        @Query(LONGITUDE_QUERY_PARAM) longitude: Number
    ): Observable<CurrentWeatherApiResponse>

    companion object {
        const val CURRENT_WEATHER_END_POINT = "weather"

        const val LATITUDE_QUERY_PARAM = "lat"
        const val LONGITUDE_QUERY_PARAM = "lon"
    }
}