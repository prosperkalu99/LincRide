package com.example.lincridetechnicalassessment.presentation.ride.rideHistory

import com.example.lincridetechnicalassessment.data.local.entity.RideEntity

data class RideHistoryUiState(
    val loading: Boolean = false,
    val rides: List<RideEntity>? = emptyList(),
)