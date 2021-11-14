package com.example.emiratesauctionsweatherapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.emiratesauctionsweatherapp.R
import com.example.emiratesauctionsweatherapp.data.model.app.CustomMessage
import com.example.emiratesauctionsweatherapp.data.model.WeatherLog
import com.example.emiratesauctionsweatherapp.data.repository.WeatherLogRepository
import com.example.emiratesauctionsweatherapp.exception.BusinessException
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _weatherLogsList: LiveData<List<WeatherLog>> =
        WeatherLogRepository(getApplication()).allWeatherLogs.asLiveData()
    val weatherLogsList: LiveData<List<WeatherLog>>
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
//        _weatherLogsList.value = mutableListOf()
    }


    fun retrieveWeatherForCoordinates(latitude: Number, longitude: Number) {
        Timber.d("retrieveWeatherForCoordinates`")

        showLoading()
        WeatherLogRepository.getInstance(getApplication())
            .retrieveWeatherLog(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ weatherLog ->
//                _weatherLogsList.value?.add(weatherLog)
//                _weatherLogsList.value = _weatherLogsList.value

                Timber.wtf(weatherLog.toString())

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