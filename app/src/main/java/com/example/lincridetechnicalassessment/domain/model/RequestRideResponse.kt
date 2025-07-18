package com.example.lincridetechnicalassessment.domain.model

data class RequestRideResponse(
    val status: String,
    val driver: DriverInfo,
    val estimatedArrival: String
)