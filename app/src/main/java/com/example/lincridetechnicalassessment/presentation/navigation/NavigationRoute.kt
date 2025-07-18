package com.example.lincridetechnicalassessment.presentation.navigation

sealed class NavigationRoute(val route: String) {
    data object Dashboard : NavigationRoute("dashboard")
    data object RequestRide : NavigationRoute("requestRide")
    data object RideHistory : NavigationRoute("rideHistory")
}