package com.melihcan.todoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.melihcan.todoapp.presentation.features.auth.LoginPage
import com.melihcan.todoapp.store.LoginPageStore

@Composable
fun Navigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginPage(navController = navController)
        }
    }
}