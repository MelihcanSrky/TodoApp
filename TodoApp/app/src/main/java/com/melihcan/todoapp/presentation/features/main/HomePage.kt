package com.melihcan.todoapp.presentation.features.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.melihcan.todoapp.extensions.getCurrentDayOfWeek
import com.melihcan.todoapp.extensions.getCurrentMonth
import com.melihcan.todoapp.extensions.getCurrentWeekOfYear
import com.melihcan.todoapp.extensions.getFirstDayOfWeek
import com.melihcan.todoapp.presentation.features.main.components.CustomDatePicker
import com.melihcan.todoapp.presentation.features.main.components.TabBar
import com.melihcan.todoapp.presentation.features.main.components.TodoList
import com.melihcan.todoapp.presentation.navigation.Screen
import com.melihcan.todoapp.presentation.theme.TodoTypo
import com.melihcan.todoapp.utils.IsSuccess
import kotlinx.coroutines.launch
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    viewModel: HomePageViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    )

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
        if (state.isTodoAdded == true) {
            bottomSheetState.bottomSheetState.hide()
            focusRequester.freeFocus()
            viewModel.commit(state.copy(isTodoAdded = false))
        }
    }
    LaunchedEffect(bottomSheetState.bottomSheetState.currentValue) {
        if (bottomSheetState.bottomSheetState.currentValue == SheetValue.Expanded) {
            focusRequester.requestFocus()
        } else {
            focusRequester.freeFocus()
        }
    }

    BottomSheetScaffold(
        sheetSwipeEnabled = true,
        scaffoldState = bottomSheetState,
        sheetContainerColor = MaterialTheme.colorScheme.onBackground,
        sheetContentColor = MaterialTheme.colorScheme.onSecondary,
        sheetTonalElevation = 4.dp,
        sheetShadowElevation = 4.dp,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            SheetContent(
                focusRequester = focusRequester,
                viewModel = viewModel
            )
        }
    ) {
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
                        IconButton(onClick = {
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = "Settings"
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(14.dp),
                            onClick = {
                                scope.launch { bottomSheetState.bottomSheetState.expand() }
                                focusRequester.requestFocus()
                            }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "FAB")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                if (state.isSuccess == IsSuccess.SUCCESS) {
                    TodoList(
                        currentDay = currentDay,
                        firstDayOfWeek = firstDayOfWeek,
                        currentMonth,
                        viewModel,
                        todos = state.todos
                    )
                } else {
                    buildBox(isSuccess = state.isSuccess)
                }
                if (state.dateDialog == true) {
                    CustomDatePicker(viewModel = viewModel)
                }
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
        else if (isSuccess == IsSuccess.ERROR)
            Text(
                text = "Somethings Wrong!",
                style = TodoTypo.headlineLarge,
                color = MaterialTheme.colorScheme.surface
            )
        else
            Text(
                text = "Add Now!",
                style = TodoTypo.headlineLarge,
                color = MaterialTheme.colorScheme.surface
            )
    }
}

@Composable
fun SheetContent(
    focusRequester: FocusRequester,
    viewModel: HomePageViewModel,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        TextField(
            value = viewModel.state.value.title.toString(),
            onValueChange = { value ->
                viewModel.commit(
                    viewModel.state.value.copy(
                        title = value.toString()
                    )
                )
            },
            textStyle = TodoTypo.bodyMedium.copy(color = MaterialTheme.colorScheme.surface),
            placeholder = {
                Text(
                    text = " Write your task",
                    style = TodoTypo.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            modifier = Modifier
                .padding(0.dp)
                .focusRequester(focusRequester),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),

            )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                    onClick = {
                        viewModel.commit(viewModel.state.value.copy(dateDialog = true))
                    }) {
                    Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "Calendar")
                    Text(
                        text = viewModel.state.value.selectedDate,
                        style = TodoTypo.bodyMedium.copy(fontSize = 12.sp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                    onClick = { }) {
                    Icon(imageVector = Icons.Outlined.List, contentDescription = "Calendar")
                    Text(text = "No List", style = TodoTypo.bodyMedium.copy(fontSize = 12.sp))
                }
            }
            FilledIconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.background,
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    viewModel.dispatch(HomePageAction.CreateTodo)
                }) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "Send")
            }
        }
    }
}



