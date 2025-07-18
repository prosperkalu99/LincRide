package com.example.lincridetechnicalassessment.data.repository

import com.example.lincridetechnicalassessment.data.local.dao.RideDao
import com.example.lincridetechnicalassessment.data.local.entity.RideEntity
import com.example.lincridetechnicalassessment.domain.repository.RideRepository
import com.example.lincridetechnicalassessment.util.FareConfig
import kotlinx.coroutines.flow.Flow

class RideRepositoryImpl(
    private val dao: RideDao
) : RideRepository {

    override suspend fun estimateFare(
        pickupAddress: String,
        destinationAddress: String,
        distance: Double,
        trafficLevel: Int,
        currentHour: Int?
    ): RideEntity {
        val baseFare = FareConfig.BASE_FARE
        val distanceFare = FareConfig.PER_KM_RATE * distance
        val demandMultiplier = FareConfig.getDemandMultiplier(currentHour ?: FareConfig.getCurrentHour())
        val trafficMultiplier = FareConfig.getTrafficMultiplier(trafficLevel)

        val totalFare = (baseFare + distanceFare) * demandMultiplier * trafficMultiplier

        return RideEntity(
            pickupLocation = pickupAddress,
            destinationLocation = destinationAddress,
            baseFare = baseFare,
            distanceFare = distanceFare,
            demandMultiplier = demandMultiplier,
            trafficMultiplier = trafficMultiplier,
            totalFare = totalFare,
            driverName = "John Doe",
            car = "Toyota Prius",
            plateNumber = "XYZ-1234",
            estimatedArrival = "5 min",
            timestamp = System.currentTimeMillis()
        )
    }

    override suspend fun requestRide(ride: RideEntity): RideEntity {
        return ride
    }

    override suspend fun saveRide(ride: RideEntity) {
        dao.insertRide(ride)
    }

    override fun getRideHistory(): Flow<List<RideEntity>> = dao.getAllRides()
}