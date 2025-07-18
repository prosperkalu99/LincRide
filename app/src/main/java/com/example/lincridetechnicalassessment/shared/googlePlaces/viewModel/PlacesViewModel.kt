package com.example.lincridetechnicalassessment.shared.googlePlaces.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placesClient: PlacesClient
) : ViewModel() {

    private val sessionToken = AutocompleteSessionToken.newInstance()

    private val _suggestions = MutableStateFlow<List<AutocompletePrediction>>(emptyList())
    val suggestions: StateFlow<List<AutocompletePrediction>> = _suggestions

    suspend fun fetchAutoCompleteSuggestions(query: String) {
        suspendCancellableCoroutine { continuation ->
            val request = FindAutocompletePredictionsRequest.builder()
                .setSessionToken(sessionToken)
                .setQuery(query)
                .setCountries(listOf("NG"))
                .build()

            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    _suggestions.value = response.autocompletePredictions
                    continuation.resume(response.autocompletePredictions)
                }
                .addOnFailureListener { exception ->
                    _suggestions.value = emptyList()
                    continuation.resumeWithException(exception)
                }
        }

    }

    suspend fun fetchLatLngFromPlaceId(placeId: String, onLatLngResult: (Double, Double) -> Unit) {
        suspendCancellableCoroutine { continuation ->
            val placeRequest = FetchPlaceRequest.builder(
                placeId,
                listOf(Place.Field.LAT_LNG)
            ).build()

            placesClient.fetchPlace(placeRequest)
                .addOnSuccessListener { response ->
                    response.place.latLng?.let { latLng ->
                        onLatLngResult(latLng.latitude, latLng.longitude)
                        continuation.resume(response.place.latLng)
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    continuation.resumeWithException(e)
                }
        }
    }
}