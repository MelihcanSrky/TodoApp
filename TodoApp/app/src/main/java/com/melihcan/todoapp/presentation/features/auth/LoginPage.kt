package com.melihcan.todoapp.presentation.features.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.melihcan.todoapp.store.LoginPageStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    store: LoginPageStore = hiltViewModel(),
    navController: NavController
) {
    var username by remember {
        mutableStateOf(store.getters("getUsername"))
    }
    var password by remember {
        mutableStateOf(store.getters("getPassword"))
    }
    Scaffold(
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(text = "Login")
            OutlinedTextField(value = username!!, onValueChange = {
                store.setUsername(it)
                username = store.getters("getUsername")
            })
            OutlinedTextField(value = password!!, onValueChange = {
                store.setPassword(it)
                password = store.getters("getPassword")
            })
            Button(onClick = {
                store.loginUser()
            }) {
                Text(text = "Login")
            }
        }
    }
}