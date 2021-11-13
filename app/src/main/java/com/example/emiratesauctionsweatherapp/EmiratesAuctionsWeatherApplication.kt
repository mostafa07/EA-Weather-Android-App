package com.example.emiratesauctionsweatherapp

import android.app.Application
import timber.log.Timber

class EmiratesAuctionsWeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}