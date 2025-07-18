package com.example.lincridetechnicalassessment.presentation.ride.requestRide.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lincridetechnicalassessment.domain.model.FareEstimateResponse
import kotlinx.coroutines.launch

@Composable
fun RideFareSheet(
    requestingRide: Boolean = false,
    fare: FareEstimateResponse,
    onRequestRide: suspend () -> Unit = {}
){
    val scope = rememberCoroutineScope()

    Column(
        Modifier.fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            "Fare Estimate: \$${fare.totalFare}",
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text("Base Fare: \$${fare.baseFare}")
        Spacer(modifier = Modifier.height(4.dp))
        Text("Distance Fare: \$${fare.distanceFare}")
        Spacer(modifier = Modifier.height(4.dp))
        Text("Demand Multiplier: ${fare.demandMultiplier}x")

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Button(
            onClick = {
                scope.launch {
                    onRequestRide()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !requestingRide
        ) {
            if(requestingRide){
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Request Ride")
            }
        }
    }
}