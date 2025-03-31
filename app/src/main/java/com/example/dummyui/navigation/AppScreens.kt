package com.example.dummyui.navigation

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login_screen")
    object SignupScreen : AppScreens("signup_screen")
    object HomeScreen : AppScreens("home_screen")
    object AssignmentsScreen : AppScreens("assignments_screen")
    object MessagesScreen : AppScreens("messages_screen")
    object ProfileScreen : AppScreens("profile_screen")
    object CreateAssignmentScreen : AppScreens("create_assignment_screen")
    object AssignmentDetailScreen : AppScreens("assignment_detail_screen/{assignmentId}")
    object ChatScreen : AppScreens("chat_screen/{chatId}")
    object OnboardingScreen : AppScreens("onboarding_screen")
}