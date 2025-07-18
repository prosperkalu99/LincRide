package com.example.lincridetechnicalassessment.shared.googlePlaces.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lincridetechnicalassessment.shared.googlePlaces.viewModel.PlacesViewModel
import com.example.lincridetechnicalassessment.ui.theme.LincRideTechnicalAssessmentTheme
import com.google.android.libraries.places.api.model.AutocompletePrediction
import kotlinx.coroutines.launch

@Composable
fun PlacesAutoCompleteTextField(
    placeHolder: String = "Search Address",
    onFetchLatLng: (Double, Double) -> Unit = { _, _ -> },
    onAddressSelected: (String) -> Unit = {}
) {

    val viewModel: PlacesViewModel = hiltViewModel()
    val suggestions by viewModel.suggestions.collectAsState()

    PlacesAutoCompleteTextFieldContent(
        placeHolder = placeHolder,
        onQueryChange = { query ->
            if (query.length >= 3) {
                viewModel.fetchAutoCompleteSuggestions(query)
            }
        },
        suggestions = suggestions,
        onSuggestionClick = { placeId, address ->
            viewModel.fetchLatLngFromPlaceId(placeId) { lat, lng ->
                onFetchLatLng(lat, lng)
            }
            onAddressSelected(address)
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesAutoCompleteTextFieldContent(
    placeHolder: String = "Search Address",
    onQueryChange: suspend (String) -> Unit = {},
    suggestions: List<AutocompletePrediction> = emptyList(),
    onSuggestionClick: suspend (String, String) -> Unit = {_, _ ->}
) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    var query by remember { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    var fetchingSuggestions by rememberSaveable { mutableStateOf(false) }
    var fetchingLatLng by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        }
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                scope.launch {
                    fetchingSuggestions = true
                    onQueryChange(query)
                    fetchingSuggestions = false
                }
                expanded = query.isNotEmpty() && suggestions.isNotEmpty()
            },
            label = { Text(placeHolder) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            trailingIcon = {
                if (fetchingSuggestions || fetchingLatLng) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                    )
                } else {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            },
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            suggestions.forEach { prediction ->
                DropdownMenuItem(
                    text = { Text(prediction.getFullText(null).toString()) },
                    onClick = {
                        expanded = false
                        query = prediction.getFullText(null).toString()
                        focusManager.clearFocus()
                        scope.launch {
                            fetchingLatLng = true
                            onSuggestionClick(prediction.placeId, query)
                            fetchingLatLng = false
                        }
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DashboardScreenPreview(){
    LincRideTechnicalAssessmentTheme {
        PlacesAutoCompleteTextFieldContent()
    }
}