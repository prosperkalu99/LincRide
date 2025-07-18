package com.example.lincridetechnicalassessment.di

import com.example.lincridetechnicalassessment.data.local.dao.RideDao
import com.example.lincridetechnicalassessment.data.repository.RideRepositoryImpl
import com.example.lincridetechnicalassessment.domain.repository.RideRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRideRepository(dao: RideDao): RideRepository =
        RideRepositoryImpl(dao)
}