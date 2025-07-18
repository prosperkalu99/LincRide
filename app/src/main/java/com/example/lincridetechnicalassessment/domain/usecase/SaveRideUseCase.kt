package com.example.lincridetechnicalassessment.domain.usecase

import com.example.lincridetechnicalassessment.data.local.entity.RideEntity
import com.example.lincridetechnicalassessment.domain.repository.RideRepository

class SaveRideUseCase(private val repository: RideRepository) {
    suspend operator fun invoke(ride: RideEntity) {
        repository.saveRide(ride)
    }
}