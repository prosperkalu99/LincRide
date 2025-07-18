package com.example.lincridetechnicalassessment.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ride_history")
data class RideEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pickupLocation: String,
    val destinationLocation: String,
    val baseFare: Double,
    val distanceFare: Double,
    val demandMultiplier: Double,
    val trafficMultiplier: Double,
    val totalFare: Double,
    val driverName: String,
    val car: String,
    val plateNumber: String,
    val estimatedArrival: String,
    val timestamp: Long
)