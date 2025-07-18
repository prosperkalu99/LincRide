package com.example.lincridetechnicalassessment.di

import android.app.Application
import androidx.room.Room
import com.example.lincridetechnicalassessment.data.local.AppDatabase
import com.example.lincridetechnicalassessment.data.local.dao.RideDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "ride_database").build()
    }

    @Provides
    @Singleton
    fun provideRideDao(db: AppDatabase): RideDao = db.rideDao()
}