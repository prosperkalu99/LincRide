package com.example.lincridetechnicalassessment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lincridetechnicalassessment.data.local.dao.RideDao
import com.example.lincridetechnicalassessment.data.local.entity.RideEntity

@Database(entities = [RideEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rideDao(): RideDao
}