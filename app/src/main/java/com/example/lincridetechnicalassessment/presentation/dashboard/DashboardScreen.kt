package com.example.lincridetechnicalassessment.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.lincridetechnicalassessment.ui.theme.LincRideTechnicalAssessmentTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onRequestRide: () -> Unit = {},
    onViewRideHistory: () -> Unit = {}
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "LincRide",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onRequestRide,
            ) {
                Text("Request a Ride")
            }

            OutlinedButton (
                onClick = onViewRideHistory,
            ) {
                Text("View Ride History")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DashboardScreenPreview(){
    LincRideTechnicalAssessmentTheme {
        DashboardScreen()
    }
}