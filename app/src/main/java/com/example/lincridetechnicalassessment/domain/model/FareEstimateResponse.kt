package com.example.lincridetechnicalassessment.domain.model

data class FareEstimateResponse(
    val baseFare: Double,
    val distanceFare: Double,
    val demandMultiplier: Double,
    val totalFare: Double
)