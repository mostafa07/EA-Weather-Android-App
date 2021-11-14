package com.example.emiratesauctionsweatherapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.emiratesauctionsweatherapp.R
import com.example.emiratesauctionsweatherapp.data.model.app.CustomMessage
import com.example.emiratesauctionsweatherapp.data.model.domain.WeatherLog
import com.example.emiratesauctionsweatherapp.data.repository.WeatherRepository
import com.example.emiratesauctionsweatherapp.exception.BusinessException
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _weatherLogsList: MutableLiveData<MutableList<WeatherLog>> = MutableLiveData()
    val weatherLogsList: LiveData<MutableList<WeatherLog>>
        get() = _weatherLogsList

    private val _successMessage: MutableLiveData<CustomMessage> = MutableLiveData()
    val successMessage: LiveData<CustomMessage>
        get() = _successMessage

    private val _errorMessage: MutableLiveData<CustomMessage> = MutableLiveData()
    val errorMessage: LiveData<CustomMessage>
        get() = _errorMessage

    private val _isContentLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isContentLoading: LiveData<Boolean>
        get() = _isContentLoading

    init {
        _isContentLoading.value = true
        _weatherLogsList.value = mutableListOf()
//        retrieveWeatherForCoordinates(30, 30)
    }


    fun retrieveWeatherForCoordinates(latitude: Number, longitude: Number) {
        Timber.d("retrieveWeatherForCoordinates`")

        showLoading()
        WeatherRepository.getInstance(getApplication())
            .getWeatherLogs(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ weatherLog ->
                _weatherLogsList.value?.add(weatherLog)
                _weatherLogsList.value = _weatherLogsList.value
                hideLoading()
            }) { throwable ->
                setErrorMessage(throwable)
                hideLoading()
            }
    }


    private fun setSuccessMessage(message: CustomMessage) {
        _successMessage.value = message
    }

    private fun setErrorMessage(errorMessage: CustomMessage) {
        _errorMessage.value = errorMessage
    }

    private fun setErrorMessage(t: Throwable) {
        if (t is BusinessException) {
            setErrorMessage(t.businessMessage)
        } else {
            t.printStackTrace()
            setErrorMessage(CustomMessage(R.string.error_operation_failed))
        }
    }

    private fun showLoading() {
        _isContentLoading.value = true
    }

    private fun hideLoading() {
        _isContentLoading.value = false
    }
}