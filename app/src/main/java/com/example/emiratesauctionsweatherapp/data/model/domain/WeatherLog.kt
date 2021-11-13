package com.example.emiratesauctionsweatherapp.data.model.domain

data class WeatherLog(
    val id: Int,
    val temperature: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val city: String,
    val country: String,
    val description: String,
    val requestDate: Long
)