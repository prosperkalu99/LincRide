package com.example.lincridetechnicalassessment.presentation.ride.requestRide

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lincridetechnicalassessment.domain.model.FareEstimateResponse
import com.example.lincridetechnicalassessment.domain.model.RequestRideResponse
import com.example.lincridetechnicalassessment.presentation.ride.requestRide.widgets.RideConfirmationSheet
import com.example.lincridetechnicalassessment.presentation.ride.requestRide.widgets.RideFareSheet
import com.example.lincridetechnicalassessment.shared.googlePlaces.widgets.PlacesAutoCompleteTextField
import com.example.lincridetechnicalassessment.ui.theme.LincRideTechnicalAssessmentTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestRideScreen(
    viewModel: RequestRideViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onNavigateToRideHistory: () -> Unit = {}
) {

    val state by viewModel.state.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                state.pickupLatLng?.first ?: 6.5192205,
                state.pickupLatLng?.second ?: 3.3720398
            ),
            12f
        )
    }
    val mapUiSettings = remember { MapUiSettings(zoomControlsEnabled = false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> }
    )

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    LaunchedEffect(state.pickupLatLng, state.destinationLatLng) {
        state.pickupLatLng?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(LatLng(it.first, it.second), 15f),
                durationMs = 2000
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Request a ride",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
                .fillMaxSize()
        ) {

            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                cameraPositionState = cameraPositionState,
                uiSettings = mapUiSettings,
                properties = MapProperties(
                    isTrafficEnabled = true
                ),
            ) {
                state.pickupLatLng?.let {
                    Marker(
                        state = MarkerState(position = LatLng(it.first, it.second)),
                        title = "Pickup"
                    )
                }
                state.destinationLatLng?.let {
                    Marker(
                        state = MarkerState(position = LatLng(it.first, it.second)),
                        title = "Destination"
                    )
                }

                val pickupLatLng = state.pickupLatLng
                val destinationLatLng = state.destinationLatLng
                if (pickupLatLng != null && destinationLatLng != null)
                    Polyline(
                        points = listOf(pickupLatLng, destinationLatLng)
                            .map { LatLng(it.first, it.second) },
                        color = Color.Blue,
                        width = 5f
                    )
            }

            RequestRideContent(
                onSetPickup = { lat, lng ->
                    viewModel.setPickup(lat, lng)
                },
                onSetDestination = { lat, lng ->
                    viewModel.setDestination(lat, lng)
                },
                onEstimateFare = { viewModel.estimateFare(trafficLevel = 1) },
                onRequestRide = viewModel::requestRide,
                shouldEstimateFare = state.destinationLatLng != null
                        && state.pickupLatLng != null,
                fare = state.fareEstimate,
                rideConfirmation = state.rideConfirmation,
                onNavigateToRideHistory = onNavigateToRideHistory,
                bookRide = viewModel::saveRide,
                onSetPickupLocation = viewModel::setPickupLocation,
                onSetDestinationLocation = viewModel::setDestinationLocation
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestRideContent(
    shouldEstimateFare: Boolean = false,
    onSetPickup: (Double, Double) -> Unit = { _, _ -> },
    onSetDestination: (Double, Double) -> Unit = { _, _ -> },
    onSetPickupLocation: (String) -> Unit = {},
    onSetDestinationLocation: (String) -> Unit = {},
    onEstimateFare: suspend () -> Unit = {},
    onRequestRide: suspend () -> Unit = {},
    fare: FareEstimateResponse? = null,
    rideConfirmation: RequestRideResponse? = null,
    onNavigateToRideHistory: () -> Unit = {},
    bookRide: suspend () -> Unit = {},
){

    val scope = rememberCoroutineScope()

    val fareSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val rideConfirmationSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var isFareSheetVisible by remember { mutableStateOf(false) }
    var isRideConfirmationSheetVisible by remember { mutableStateOf(false) }

    var estimatingFare by remember { mutableStateOf(false) }
    var requestingRide by remember { mutableStateOf(false) }
    var confirmingRide by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(vertical = 20.dp)
    ){

        PlacesAutoCompleteTextField(
            placeHolder = "Pickup Location",
            onFetchLatLng = { lat, lng ->
                println("Selected LatLng: $lat, $lng")
                onSetPickup(lat, lng)
            },
            onAddressSelected = onSetPickupLocation
        )

        Spacer(modifier = Modifier.height(16.dp))

        PlacesAutoCompleteTextField(
            placeHolder = "Destination",
            onFetchLatLng = { lat, lng ->
                println("Selected LatLng: $lat, $lng")
                onSetDestination(lat, lng)
            },
            onAddressSelected = onSetDestinationLocation
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                scope.launch {
                    estimatingFare = true
                    onEstimateFare()
                    estimatingFare = false
                    isFareSheetVisible = true
                }

            }, // Simulated Traffic Level
            modifier = Modifier.fillMaxWidth(),
            enabled = shouldEstimateFare && !estimatingFare,
        ) {
            if(estimatingFare){
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Estimate Fare")
            }
        }

    }

    if(isFareSheetVisible)
        fare?.let {
            ModalBottomSheet(
                onDismissRequest = { isFareSheetVisible = false },
                sheetState = fareSheetState,
                shape = RoundedCornerShape(
                    topStart = 8.dp, topEnd = 8.dp
                ),
                dragHandle = { },
                containerColor = MaterialTheme.colorScheme.background
            ) {
                RideFareSheet(
                    fare = it,
                    requestingRide = requestingRide,
                    onRequestRide = {
                        scope.launch {
                            requestingRide = true
                            onRequestRide()
                            requestingRide = false
                            isRideConfirmationSheetVisible = true
                        }
                    }
                )
            }
        }

    if(isRideConfirmationSheetVisible)
        rideConfirmation?.let {
            ModalBottomSheet(
                onDismissRequest = { isRideConfirmationSheetVisible = false },
                sheetState = rideConfirmationSheetState,
                shape = RoundedCornerShape(
                    topStart = 8.dp, topEnd = 8.dp
                ),
                dragHandle = { },
                containerColor = MaterialTheme.colorScheme.background
            ) {
                RideConfirmationSheet(
                    confirmingRide = confirmingRide,
                    rideConfirmation = it,
                    onConfirmation = {
                        scope.launch {
                            confirmingRide = true
                            bookRide()
                            confirmingRide = false

                            isFareSheetVisible = false
                            isRideConfirmationSheetVisible = false
                            onNavigateToRideHistory()
                        }
                    }
                )
            }
        }
}

@Composable
@Preview(showBackground = true)
fun DashboardScreenPreview(){
    LincRideTechnicalAssessmentTheme {
        RequestRideContent()
    }
}