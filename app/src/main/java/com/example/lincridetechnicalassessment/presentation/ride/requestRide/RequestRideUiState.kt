package com.example.lincridetechnicalassessment.presentation.ride.requestRide

import com.example.lincridetechnicalassessment.domain.model.FareEstimateResponse
import com.example.lincridetechnicalassessment.domain.model.RequestRideResponse

data class RequestRideUiState(
    val pickupLatLng: Pair<Double, Double>? = null,
    val destinationLatLng: Pair<Double, Double>? = null,
    val pickupLocation: String? = null,
    val destinationLocation: String? = null,
    val fareEstimate: FareEstimateResponse? = null,
    val rideConfirmation: RequestRideResponse? = null,
)