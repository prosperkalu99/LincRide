package com.example.lincridetechnicalassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.lincridetechnicalassessment.presentation.navigation.AppNavHost
import com.example.lincridetechnicalassessment.ui.theme.LincRideTechnicalAssessmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LincRideTechnicalAssessmentTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}