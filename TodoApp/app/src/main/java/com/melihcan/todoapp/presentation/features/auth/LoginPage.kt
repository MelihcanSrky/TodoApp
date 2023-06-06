package com.melihcan.todoapp.presentation.features.auth

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.melihcan.todoapp.extensions.isPasswordValid
import com.melihcan.todoapp.extensions.isUsernameValid
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
            viewModel.dispatch(Action.ResetState)
        }
    }

    Scaffold(
        modifier = Modifier.padding(horizontal = 30.dp)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(96.dp))
            Text(text = "Login", style = TodoTypo.headlineLarge)
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = state.username,
                onValueChange = { viewModel.dispatch(Action.UpdateUsername(it)) },
                shape = MCSRadiusMedium,
                isError = if (state.username == "") false else !state.username.isUsernameValid(),
                label = {
                    Text(text = "Username")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.dispatch(Action.UpdatePassword(it)) },
                shape = MCSRadiusMedium,
                isError = if (state.password == "") false else  !state.password.isPasswordValid(),
                label = {
                    Text(text = "Password")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                contentAlignment =
                if (state.isSuccess == IsSuccess.LOADING) Alignment.Center
                else Alignment.CenterEnd
            ) {
                if (state.isSuccess == IsSuccess.LOADING)
                    CircularProgressIndicator(
                        modifier = Modifier
                            .height(18.dp)
                            .width(18.dp)
                    )
                else if (state.isSuccess == IsSuccess.ERROR)
                    Text(
                        "Error",
                        style = TodoTypo.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.End
                    )
            }
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp),
                shape = MCSRadiusMedium,
                enabled = state.isButtonEnabled,
                onClick = {
                    viewModel.dispatch(Action.LoginUser)
                }) {
                Text(text = "Login")
            }
        }
    }
}