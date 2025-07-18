package com.example.lincridetechnicalassessment.presentation.ride.rideHistory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lincridetechnicalassessment.data.local.entity.RideEntity
import com.example.lincridetechnicalassessment.presentation.ride.rideHistory.widgets.RideItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideHistoryScreen(
    viewModel: RideHistoryViewModel = hiltViewModel(),
    onRequestNewRide: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        viewModel.getRideHistory()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Ride History",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                actions = {
                    TextButton(
                        onClick = onRequestNewRide
                    ) {
                        Text("Request New Ride")
                    }
                }
            )
        },
    ){ padding ->
        Box(
            modifier = Modifier.padding(padding)
                .fillMaxSize()
        ){
            if(state.loading){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                RideHistoryContent(
                    rides = state.rides ?: emptyList()
                )
            }
        }
    }
}

@Composable
fun RideHistoryContent(
    rides: List<RideEntity> = emptyList()
){
    if(rides.isEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text("No rides found")
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(rides, key = { it.id }){
                RideItem(ride = it)
            }
        }
    }
}