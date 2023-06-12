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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            if (state.isSuccess == IsSuccess.SUCCESS) {
                buildTodoList(
                    currentDay = currentDay,
                    firstDayOfWeek = firstDayOfWeek,
                    currentMonth,
                    todos = state.todos
                )
            } else if (state.isSuccess == IsSuccess.LOADING) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Add Now!",
                        style = TodoTypo.headlineLarge,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}

@Composable
fun buildListTile(todo: TodosModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp)
                    .background(MaterialTheme.colorScheme.onBackground)
                    .border(0.dp, Color.Transparent, RoundedCornerShape(6.dp))
            ) {
                Checkbox(
                    modifier = Modifier
                        .padding(0.dp)
                        .size(24.dp)
                        .background(Color.Transparent),
                    checked = todo.isChecked,
                    onCheckedChange = {},
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.Transparent,
                        checkedColor = Color.Transparent,
                        checkmarkColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
            Text(
                text = todo.title,
                style = TodoTypo.bodyMedium,
                color = MaterialTheme.colorScheme.surface
            )
        }
        Text(
            text = todo.category,
            style = TodoTypo.bodySmall,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
    Divider(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f))
}

@Composable
fun buildTodoList(
    currentDay: Int,
    firstDayOfWeek: Int,
    currentMonth: String,
    todos: List<TodosModel>
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        for (i in currentDay..6) {
            val currentTodos =
                todos.filter { it.dayOfWeek == i }
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = (
                            if (i == currentDay) "Today - "
                            else if (i - 1 == currentDay) "Tomorrow - "
                            else {
                                currentMonth + " " + (firstDayOfWeek + 2 +
                                        (i - currentDay)).toString() + " - "
                            }
                            ) + week[currentDay + (i - currentDay)],
                    style = TodoTypo.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Divider(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f))
            }
            if (currentTodos.isNotEmpty()) {
                items(currentTodos) { todo ->
                    buildListTile(todo = todo)
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = week[currentDay + (i - currentDay)] + " is Empty!",
                            style = TodoTypo.bodyMedium,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
            item {
                Divider(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f))
            }
        }
    }
}