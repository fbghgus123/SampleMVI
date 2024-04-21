package com.example.samplemvi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.samplemvi.ui.screen.home.HomeRoute
import com.example.samplemvi.ui.screen.second.SecondRoute

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeRoute(
                navigateToSecondPage = { navController.navigate("second") }
            )
        }

        composable("second") {
            SecondRoute()
        }
    }
}