package com.example.lincridetechnicalassessment.di

import com.example.lincridetechnicalassessment.domain.repository.RideRepository
import com.example.lincridetechnicalassessment.domain.usecase.EstimateFareUseCase
import com.example.lincridetechnicalassessment.domain.usecase.GetRideHistoryUseCase
import com.example.lincridetechnicalassessment.domain.usecase.RequestRideUseCase
import com.example.lincridetechnicalassessment.domain.usecase.SaveRideUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides fun provideEstimateFareUseCase(repository: RideRepository) = EstimateFareUseCase(repository)

    @Provides fun provideRequestRideUseCase(repository: RideRepository) = RequestRideUseCase(repository)

    @Provides fun provideSaveRideUseCase(repository: RideRepository) = SaveRideUseCase(repository)

    @Provides fun provideGetRideHistoryUseCase(repository: RideRepository) = GetRideHistoryUseCase(repository)
}