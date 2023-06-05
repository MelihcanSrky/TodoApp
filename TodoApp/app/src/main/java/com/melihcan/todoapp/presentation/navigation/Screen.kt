package com.melihcan.todoapp.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
}
