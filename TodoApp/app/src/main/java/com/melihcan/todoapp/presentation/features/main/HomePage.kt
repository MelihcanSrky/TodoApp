package com.melihcan.todoapp.presentation.features.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
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
import androidx.compose.material3.Switch
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.melihcan.todoapp.R
import com.melihcan.todoapp.extensions.SetTheme
import com.melihcan.todoapp.extensions.getCurrentDayOfWeek
import com.melihcan.todoapp.extensions.getCurrentMonth
import com.melihcan.todoapp.extensions.getCurrentWeekOfYear
import com.melihcan.todoapp.extensions.getFirstDayOfWeek
import com.melihcan.todoapp.model.ListModel
import com.melihcan.todoapp.model.week
import com.melihcan.todoapp.presentation.features.main.components.CreateTaskPanel
import com.melihcan.todoapp.presentation.features.main.components.CustomDatePicker
import com.melihcan.todoapp.presentation.features.main.components.ListsPanel
import com.melihcan.todoapp.presentation.features.main.components.SettingsPanel
import com.melihcan.todoapp.presentation.features.main.components.TabBar
import com.melihcan.todoapp.presentation.features.main.components.TodoList
import com.melihcan.todoapp.presentation.navigation.Screen
import com.melihcan.todoapp.presentation.theme.TodoTypo
import com.melihcan.todoapp.utils.IsSuccess
import dagger.Lazy
import kotlinx.coroutines.launch
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    viewModel: HomePageViewModel = hiltViewModel(),
    navController: NavController,
    setTheme: SetTheme
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
            scope.launch { bottomSheetState.bottomSheetState.hide() }
            viewModel.commit(state.copy(isTodoAdded = false))
        }
        if (state.sheetPanel == SheetPanel.NONE) {
            if (bottomSheetState.bottomSheetState.currentValue == SheetValue.Expanded) {
                scope.launch { bottomSheetState.bottomSheetState.hide() }
            }
        }
    }
    LaunchedEffect(bottomSheetState.bottomSheetState.currentValue) {
        if (state.sheetPanel == SheetPanel.CREATE_TASK) {
            if (bottomSheetState.bottomSheetState.currentValue == SheetValue.Expanded) {
                focusRequester.requestFocus()
            } else {
                focusRequester.freeFocus()
            }
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
            if (state.sheetPanel == SheetPanel.CREATE_TASK)
                CreateTaskPanel(
                    focusRequester = focusRequester,
                    viewModel = viewModel
                )
            else if (state.sheetPanel == SheetPanel.LISTS)
                ListsPanel(viewModel)
            else if (state.sheetPanel == SheetPanel.SETTINGS)
                SettingsPanel(setTheme)
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
                        IconButton(onClick = {
                            viewModel.commit(state.copy(sheetPanel = SheetPanel.LISTS))
                            scope.launch { bottomSheetState.bottomSheetState.expand() }
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                        IconButton(onClick = {
                            viewModel.commit(state.copy(sheetPanel = SheetPanel.SETTINGS))
                            scope.launch { bottomSheetState.bottomSheetState.expand() }
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
                                viewModel.commit(state.copy(sheetPanel = SheetPanel.CREATE_TASK))
                                scope.launch { bottomSheetState.bottomSheetState.expand() }
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
                    if (state.todos.isEmpty()) {
                        buildBox(isSuccess = state.isSuccess)
                    }
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
                text = stringResource(id = R.string.someThingIsWrong),
                style = TodoTypo.headlineLarge,
                color = MaterialTheme.colorScheme.surface
            )
        else
            Text(
                text = stringResource(id = R.string.addNow),
                style = TodoTypo.headlineLarge,
                color = MaterialTheme.colorScheme.surface
            )
    }
}


