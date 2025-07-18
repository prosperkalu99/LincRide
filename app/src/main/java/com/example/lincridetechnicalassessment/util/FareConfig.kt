package com.example.lincridetechnicalassessment.util

import java.time.LocalTime

object FareConfig {

    const val BASE_FARE = 2.5
    const val PER_KM_RATE = 1.0

    private val peakHours = listOf(
        7..9,   // Morning rush
        17..19  // Evening rush
    )

    fun getDemandMultiplier(currentHour: Int): Double {
        return if (peakHours.any { currentHour in it }) 1.5 else 1.0
    }

    fun getTrafficMultiplier(trafficLevel: Int): Double {
        return when (trafficLevel) {
            in 0..2 -> 1.0  // Low traffic
            in 3..4 -> 1.2  // Medium
            else -> 1.3     // High
        }
    }

    fun getCurrentHour(): Int = LocalTime.now().hour
}