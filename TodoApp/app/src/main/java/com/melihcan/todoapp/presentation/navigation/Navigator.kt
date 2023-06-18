package com.melihcan.todoapp.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.melihcan.todoapp.extensions.SetTheme
import com.melihcan.todoapp.presentation.features.auth.LoginPage
import com.melihcan.todoapp.presentation.features.auth.LoginViewModel
import com.melihcan.todoapp.presentation.features.auth.RegisterPage
import com.melihcan.todoapp.presentation.features.main.HomePage
import com.melihcan.todoapp.storage.SharedPrefManager

@Composable
fun Navigator(
    context: Context,
    setTheme: SetTheme
) {
    val navController = rememberNavController()
    val token = SharedPrefManager.getInstance(context).token.token
    val user = SharedPrefManager.getInstance(context).user.username
    println(token)
    println(user)
    var startDest = Screen.Register.route
    if (token != "null" && user != "null") {
        startDest = Screen.Home.route
    }
    NavHost(
        navController = navController,
        startDestination = startDest
    ) {
        composable(Screen.Login.route) {
            LoginPage(navController = navController)
        }

        composable(Screen.Register.route) {
            RegisterPage(navController = navController)
        }

        composable(Screen.Home.route) {
            HomePage(navController = navController, setTheme = setTheme)
        }
    }
}