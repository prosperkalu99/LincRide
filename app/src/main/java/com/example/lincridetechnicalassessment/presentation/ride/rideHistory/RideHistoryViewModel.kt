package com.example.lincridetechnicalassessment.presentation.ride.rideHistory

import androidx.lifecycle.ViewModel
import com.example.lincridetechnicalassessment.domain.usecase.GetRideHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class RideHistoryViewModel @Inject constructor(
    private val getRideHistoryUseCase: GetRideHistoryUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(RideHistoryUiState())
    val state: StateFlow<RideHistoryUiState> = _state

    suspend fun getRideHistory(){
        _state.value = _state.value.copy(loading = true)
        getRideHistoryUseCase().collectLatest {
            _state.value = _state.value.copy(rides = it, loading = false)
        }
    }

}