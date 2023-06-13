package com.melihcan.todoapp.presentation.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.melihcan.todoapp.extensions.getCurrentDayOfWeek
import com.melihcan.todoapp.extensions.getCurrentMonth
import com.melihcan.todoapp.extensions.getCurrentWeekOfYear
import com.melihcan.todoapp.extensions.getFirstDayOfWeek
import com.melihcan.todoapp.model.TodosModel
import com.melihcan.todoapp.model.week
import com.melihcan.todoapp.utils.IsSuccess
import com.melihcan.todoapp.presentation.features.main.components.TabBar
import com.melihcan.todoapp.presentation.features.main.components.TodoList
import com.melihcan.todoapp.presentation.navigation.Screen
import com.melihcan.todoapp.presentation.theme.TodoTypo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    viewModel: HomePageViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value

    val currentDay = getCurrentDayOfWeek()
    val currentWeek = getCurrentWeekOfYear()
    val firstDayOfWeek = getFirstDayOfWeek(currentWeek)
    val currentMonth = getCurrentMonth()

    LaunchedEffect(state) {
        if (state.isLogin == false) {
            navController.navigate(Screen.Register.route) {
                popUpTo(Screen.Register.route)
            }
        }
    }

    Scaffold(
        topBar = {
            TabBar(
                currentDay,
                firstDayOfWeek,
                viewModel
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.shadow(
                    4.dp,
                    RectangleShape,
                    clip = true,
                    ambientColor = MaterialTheme.colorScheme.surface,
                    spotColor = MaterialTheme.colorScheme.surface
                ),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Settings")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(14.dp),
                        onClick = {}) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "FAB")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            if (state.isSuccess == IsSuccess.SUCCESS) {
                TodoList(
                    currentDay = currentDay,
                    firstDayOfWeek = firstDayOfWeek,
                    currentMonth,
                    todos = state.todos
                )
            } else {
                buildBox(isSuccess = state.isSuccess)
            }
        }
    }
}

@Composable
fun buildBox(
    isSuccess: IsSuccess
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        if (isSuccess == IsSuccess.LOADING)
            CircularProgressIndicator()
        else
            Text(
                text = "Add Now!",
                style = TodoTypo.headlineLarge,
                color = MaterialTheme.colorScheme.surface
            )
    }
}



