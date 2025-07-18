package com.example.lincridetechnicalassessment.domain.usecase

import com.example.lincridetechnicalassessment.data.local.entity.RideEntity
import com.example.lincridetechnicalassessment.domain.model.DriverInfo
import com.example.lincridetechnicalassessment.domain.model.RequestRideResponse
import com.example.lincridetechnicalassessment.domain.repository.RideRepository

class RequestRideUseCase(private val repository: RideRepository) {
    suspend operator fun invoke(ride: RideEntity): RequestRideResponse {
        repository.requestRide(ride)
        return RequestRideResponse(
            status = "confirmed",
            driver = DriverInfo(
                name = ride.driverName,
                car = ride.car,
                plateNumber = ride.plateNumber
            ),
            estimatedArrival = ride.estimatedArrival
        )
    }
}