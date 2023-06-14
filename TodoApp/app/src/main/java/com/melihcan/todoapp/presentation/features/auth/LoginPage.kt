package com.melihcan.todoapp.presentation.features.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.melihcan.todoapp.extensions.isPasswordValid
import com.melihcan.todoapp.extensions.isUsernameValid
import com.melihcan.todoapp.presentation.features.auth.components.TodoTextField
import com.melihcan.todoapp.utils.IsSuccess
import com.melihcan.todoapp.presentation.navigation.Screen
import com.melihcan.todoapp.presentation.theme.MCSRadiusMedium
import com.melihcan.todoapp.presentation.theme.TodoTypo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value

    LaunchedEffect(state) {
        if (state.isSuccess == IsSuccess.SUCCESS) {
            navController.navigate(Screen.Home.route)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.dispatch(LoginAction.ResetState)
        }
    }

    Scaffold(
        modifier = Modifier.padding(horizontal = 30.dp)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(96.dp))
                Text(
                    text = "Login",
                    style = TodoTypo.headlineLarge,
                    color = MaterialTheme.colorScheme.surface
                )
                Spacer(modifier = Modifier.height(24.dp))
                TodoTextField(
                    value = state.username,
                    onValueChange = {
                        viewModel.dispatch(LoginAction.UpdateUsername(it))
                    },
                    isError = if (state.username == "") false else !state.username.isUsernameValid(),
                    label = "Username"
                )
                Spacer(modifier = Modifier.height(8.dp))
                TodoTextField(
                    value = state.password,
                    onValueChange = {
                        viewModel.dispatch(LoginAction.UpdatePassword(it))
                    },
                    isError = if (state.password == "") false else !state.password.isPasswordValid(),
                    label = "Password",
                    isPassword = true
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment =
                    if (state.isSuccess == IsSuccess.LOADING) Alignment.Center
                    else Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (state.isSuccess == IsSuccess.ERROR)
                            Text(
                                text = "Somethings is wrong!",
                                style = TodoTypo.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        else
                            Spacer(modifier = Modifier.width(1.dp))
                        TextButton(
                            contentPadding = PaddingValues(4.dp),
                            onClick = {}) {
                            Text(
                                text = "Forgot Password",
                                style = TodoTypo.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    shape = MCSRadiusMedium,
                    enabled = state.isButtonEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.onBackground,
                        disabledContentColor = MaterialTheme.colorScheme.surface
                    ),
                    onClick = {
                        viewModel.dispatch(LoginAction.LoginUser)
                    }) {
                    if (state.isSuccess == IsSuccess.LOADING)
                        CircularProgressIndicator(
                            modifier = Modifier
                                .height(18.dp)
                                .width(18.dp),
                            color = MaterialTheme.colorScheme.surface
                        )
                    else
                        Text(text = "Login", style = TodoTypo.bodyMedium)
                }
            }
            TextButton(onClick = {
                navController.navigate(Screen.Register.route)
            }) {
                Text(
                    text = "Sign Up Now",
                    style = TodoTypo.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}