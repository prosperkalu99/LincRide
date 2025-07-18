package com.example.lincridetechnicalassessment.presentation.ride.rideHistory.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lincridetechnicalassessment.data.local.entity.RideEntity

@Composable
fun RideItem(ride: RideEntity) {
    Card (
        Modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Pickup: ${ride.pickupLocation}")
            Text("Destination: ${ride.destinationLocation}")

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text("Driver: ${ride.driverName}")
            Text("Car: ${ride.car}")
            Text("Plate Number: ${ride.plateNumber}")
            Text("Arrival: ${ride.estimatedArrival}")

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text("Fare: \$${ride.totalFare}")
        }
    }
}