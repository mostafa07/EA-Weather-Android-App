package com.example.emiratesauctionsweatherapp.exception

import com.example.emiratesauctionsweatherapp.data.model.app.CustomMessage

class BusinessException(val businessMessage: CustomMessage) : Exception()