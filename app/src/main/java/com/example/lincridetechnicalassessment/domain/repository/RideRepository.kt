package com.example.lincridetechnicalassessment.domain.repository

import com.example.lincridetechnicalassessment.data.local.entity.RideEntity
import kotlinx.coroutines.flow.Flow

interface RideRepository {
    suspend fun estimateFare(
        pickupAddress: String,
        destinationAddress: String,
        distance: Double,
        trafficLevel: Int,
        currentHour: Int? = null
    ): RideEntity

    suspend fun requestRide(ride: RideEntity): RideEntity
    suspend fun saveRide(ride: RideEntity)
    fun getRideHistory(): Flow<List<RideEntity>>
}