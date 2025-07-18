package com.example.lincridetechnicalassessment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lincridetechnicalassessment.data.local.entity.RideEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RideDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRide(ride: RideEntity)

    @Query("SELECT * FROM ride_history ORDER BY timestamp DESC")
    fun getAllRides(): Flow<List<RideEntity>>
}