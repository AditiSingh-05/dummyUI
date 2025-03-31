package com.example.dummyui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dummyui.screens.*
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route,
        modifier = modifier
    ) {
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(AppScreens.SignupScreen.route) {
            SignupScreen(navController)
        }
        composable(AppScreens.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(AppScreens.OnboardingScreen.route){
            OnboardingScreen(navController)
        }
        composable(AppScreens.AssignmentsScreen.route) {
            AssignmentsScreen(navController)
        }
        composable(AppScreens.MessagesScreen.route) {
            MessagesScreen(navController)
        }
        composable(AppScreens.ProfileScreen.route) {
            ProfileScreen(navController)
        }




    }
}
