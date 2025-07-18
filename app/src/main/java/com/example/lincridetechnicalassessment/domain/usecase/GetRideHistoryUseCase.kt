package com.example.lincridetechnicalassessment.domain.usecase

import com.example.lincridetechnicalassessment.data.local.entity.RideEntity
import com.example.lincridetechnicalassessment.domain.repository.RideRepository
import kotlinx.coroutines.flow.Flow

class GetRideHistoryUseCase(private val repository: RideRepository) {
    operator fun invoke(): Flow<List<RideEntity>> = repository.getRideHistory()
}