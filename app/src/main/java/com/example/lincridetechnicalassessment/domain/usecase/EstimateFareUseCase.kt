package com.example.lincridetechnicalassessment.domain.usecase

import com.example.lincridetechnicalassessment.domain.model.FareEstimateResponse
import com.example.lincridetechnicalassessment.domain.repository.RideRepository

class EstimateFareUseCase(private val repository: RideRepository) {
    suspend operator fun invoke(
        pickupAddress: String,
        destinationAddress: String,
        distance: Double,
        trafficLevel: Int,
        currentHour: Int?
    ): FareEstimateResponse {
        val ride = repository.estimateFare(
            pickupAddress,
            destinationAddress,
            distance,
            trafficLevel,
            currentHour,
        )
        return FareEstimateResponse(
            baseFare = ride.baseFare,
            distanceFare = ride.distanceFare,
            demandMultiplier = ride.demandMultiplier * ride.trafficMultiplier,
            totalFare = ride.totalFare
        )
    }
}