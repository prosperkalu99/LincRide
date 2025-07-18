package com.example.lincridetechnicalassessment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lincridetechnicalassessment.presentation.dashboard.DashboardScreen
import com.example.lincridetechnicalassessment.presentation.ride.requestRide.RequestRideScreen
import com.example.lincridetechnicalassessment.presentation.ride.rideHistory.RideHistoryScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoute.Dashboard.route) {

        composable(NavigationRoute.Dashboard.route) {
            DashboardScreen(
                onRequestRide = { navController.navigate(NavigationRoute.RequestRide.route) },
                onViewRideHistory = { navController.navigate(NavigationRoute.RideHistory.route) }
            )
        }

        composable(NavigationRoute.RequestRide.route) {
            RequestRideScreen(
                onBack = { navController.popBackStack() },
                onNavigateToRideHistory = {
                    navController.navigate(
                        NavigationRoute.RideHistory.route
                    ) {
                        popUpTo(NavigationRoute.Dashboard.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(NavigationRoute.RideHistory.route) {
            RideHistoryScreen(
                onRequestNewRide = {
                    navController.navigate(
                        NavigationRoute.RequestRide.route
                    ) {
                        popUpTo(NavigationRoute.Dashboard.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}