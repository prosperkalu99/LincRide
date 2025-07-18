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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lincridetechnicalassessment.domain.model.RequestRideResponse

@Composable
fun RideConfirmationSheet(
    confirmingRide: Boolean = false,
    rideConfirmation: RequestRideResponse,
    onConfirmation: () -> Unit = {}
){

    Column(
        Modifier.fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            "Driver Found",
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text("Driver: ${rideConfirmation.driver.name}")
        Spacer(modifier = Modifier.height(4.dp))
        Text("Car: ${rideConfirmation.driver.car}")
        Spacer(modifier = Modifier.height(4.dp))
        Text("Plate Number: ${rideConfirmation.driver.plateNumber}")
        Spacer(modifier = Modifier.height(4.dp))
        Text("Arrival: ${rideConfirmation.estimatedArrival}")

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Button(
            onClick = onConfirmation,
            modifier = Modifier.fillMaxWidth(),
            enabled = !confirmingRide
        ) {
            if(confirmingRide){
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Proceed")
            }
        }
    }
}