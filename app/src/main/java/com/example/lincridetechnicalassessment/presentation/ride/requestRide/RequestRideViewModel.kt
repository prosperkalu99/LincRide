package com.example.lincridetechnicalassessment.presentation.ride.requestRide

import androidx.lifecycle.ViewModel
import com.example.lincridetechnicalassessment.data.local.entity.RideEntity
import com.example.lincridetechnicalassessment.domain.usecase.EstimateFareUseCase
import com.example.lincridetechnicalassessment.domain.usecase.RequestRideUseCase
import com.example.lincridetechnicalassessment.domain.usecase.SaveRideUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.math.sqrt

@HiltViewModel
class RequestRideViewModel @Inject constructor(
    private val estimateFare: EstimateFareUseCase,
    private val requestRide: RequestRideUseCase,
    private val saveRide: SaveRideUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RequestRideUiState())
    val state: StateFlow<RequestRideUiState> = _state

    fun setPickup(lat: Double, lng: Double) {
        _state.value = _state.value.copy(pickupLatLng = lat to lng, fareEstimate = null)
    }

    fun setPickupLocation(location: String) {
        _state.value = _state.value.copy(pickupLocation = location)
    }

    fun setDestination(lat: Double, lng: Double) {
        _state.value = _state.value.copy(destinationLatLng = lat to lng, fareEstimate = null)
    }

    fun setDestinationLocation(location: String) {
        _state.value = _state.value.copy(destinationLocation = location)
    }

    suspend fun estimateFare(trafficLevel: Int) {
        val pickup = _state.value.pickupLatLng
        val dest = _state.value.destinationLatLng
        val pickupLocation = _state.value.pickupLocation
        val destLocation = _state.value.destinationLocation

        if (pickup != null && dest != null && pickupLocation != null && destLocation != null) {
            val distance = calculateDistance(pickup.first, pickup.second, dest.first, dest.second)
            delay(1000)
            val fare = estimateFare(
                pickupLocation,
                destLocation,
                distance,
                trafficLevel,
                currentHour = null
            )
            _state.value = _state.value.copy(fareEstimate = fare)
        }
    }

    suspend fun requestRide() {
        val pickup = _state.value.pickupLatLng
        val dest = _state.value.destinationLatLng
        val pickupLocation = _state.value.pickupLocation
        val destLocation = _state.value.destinationLocation
        val fare = _state.value.fareEstimate

        if (pickup != null && dest != null && fare != null && pickupLocation != null && destLocation != null) {
            val rideEntity = RideEntity(
                pickupLocation = pickupLocation,
                destinationLocation = destLocation,
                baseFare = fare.baseFare,
                distanceFare = fare.distanceFare,
                demandMultiplier = fare.demandMultiplier,
                trafficMultiplier = 1.0, // already factored in demandMultiplier
                totalFare = fare.totalFare,
                driverName = "John Doe",
                car = "Toyota Prius",
                plateNumber = "XYZ-1234",
                estimatedArrival = "5 min",
                timestamp = System.currentTimeMillis()
            )

            delay(1000)
            val confirmation = requestRide(rideEntity)
            _state.value = _state.value.copy(
                rideConfirmation = confirmation,
            )
        }
    }

    suspend fun saveRide() {
        val pickupLocation = _state.value.pickupLocation
        val destLocation = _state.value.destinationLocation
        val fare = _state.value.fareEstimate
        val rideConfirmation = _state.value.rideConfirmation

        if (fare != null && rideConfirmation != null && pickupLocation != null && destLocation != null) {
            val rideEntity = RideEntity(
                pickupLocation = pickupLocation,
                destinationLocation = destLocation,
                baseFare = fare.baseFare,
                distanceFare = fare.distanceFare,
                demandMultiplier = fare.demandMultiplier,
                trafficMultiplier = 1.0, // already factored in demandMultiplier
                totalFare = fare.totalFare,
                driverName = rideConfirmation.driver.name,
                car = rideConfirmation.driver.car,
                plateNumber = rideConfirmation.driver.plateNumber,
                estimatedArrival = rideConfirmation.estimatedArrival,
                timestamp = System.currentTimeMillis()
            )

            delay(1000)
            saveRide(rideEntity)
        }

    }

    private fun calculateDistance(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val r = 6371 // km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) *
                Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(sqrt(a), sqrt(1 - a))
        return (r * c).roundToInt().toDouble()
    }

}