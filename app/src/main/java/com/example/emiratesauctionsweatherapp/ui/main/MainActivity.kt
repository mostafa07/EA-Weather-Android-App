package com.example.emiratesauctionsweatherapp.ui.main

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.emiratesauctionsweatherapp.BR
import com.example.emiratesauctionsweatherapp.R
import com.example.emiratesauctionsweatherapp.data.model.app.CustomMessage
import com.example.emiratesauctionsweatherapp.databinding.ActivityMainBinding
import com.example.emiratesauctionsweatherapp.ui.adapter.WeatherLogAdapter
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mWeatherLogAdapter: WeatherLogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupRecyclerView()
        setupViewModel()
        setupViewModelObservations()
    }

    private fun setupRecyclerView() {
        mWeatherLogAdapter = WeatherLogAdapter { weatherLog, _ ->
            // TODO add details screen and implement this onclick listener
        }
        mBinding.weatherLogsRecyclerView.adapter = mWeatherLogAdapter
    }

    private fun setupViewModel() {
        mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mBinding.setVariable(BR.viewModel, mMainViewModel)
        mBinding.lifecycleOwner = this@MainActivity
        mBinding.executePendingBindings()
    }

    private fun setupViewModelObservations() {
        mMainViewModel.successMessage.observe(this, { showSnackbar(it, true) })
        mMainViewModel.errorMessage.observe(this, { showSnackbar(it, false) })
        mMainViewModel.isContentLoading.observe(this) { isLoading ->
            mBinding.shimmerLayout.shimmerFrameLayout.showShimmer(isLoading)

            if (isLoading) {
                disableUserInteraction()
            } else {
                reEnableUserInteraction()
            }
        }

        mMainViewModel.weatherLogsList.observe(this, {
            mWeatherLogAdapter.dataList = it
            mBinding.weatherLogsRecyclerView.smoothScrollToPosition(0)
        })
    }

    private fun disableUserInteraction() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun reEnableUserInteraction() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun showSnackbar(message: CustomMessage, successFlag: Boolean) {
        val messageString = getString(message.messageResourceId, message.params)

        Snackbar.make(mBinding.root, messageString, Snackbar.LENGTH_LONG)
            .setBackgroundTint(
                resources.getColor(
                    if (successFlag) android.R.color.holo_green_dark else android.R.color.holo_red_dark
                )
            )
            .setTextColor(resources.getColor(android.R.color.white))
            .show()
    }

}