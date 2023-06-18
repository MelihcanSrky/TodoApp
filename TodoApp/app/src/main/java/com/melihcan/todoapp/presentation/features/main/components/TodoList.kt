package com.melihcan.todoapp.presentation.features.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.melihcan.todoapp.R
import com.melihcan.todoapp.extensions.getCurrentWeekOfYear
import com.melihcan.todoapp.model.TodosModel
import com.melihcan.todoapp.model.week
import com.melihcan.todoapp.presentation.features.main.HomePageViewModel
import com.melihcan.todoapp.presentation.theme.TodoTypo

@Composable
fun TodoList(
    currentDay: Int,
    firstDayOfWeek: Int,
    currentMonth: String,
    viewModel: HomePageViewModel,
    todos: List<TodosModel>
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        for (i in currentDay..6) {
            val currentTodos =
                todos.filter { it.dayOfWeek == i && it.weekOfYear == getCurrentWeekOfYear() }
            if (currentTodos.isEmpty()) {
                continue
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = (
                            if (i == currentDay) stringResource(id = R.string.today) + " - "
                            else if (i - 1 == currentDay) stringResource(id = R.string.tomorrow) + " - "
                            else {
                                currentMonth + " " + (firstDayOfWeek + 2 +
                                        (i - currentDay)).toString() + " - "
                            }
                            ) + stringResource(id = week[currentDay + (i - currentDay)]),
                    style = TodoTypo.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Divider(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f))
            }
            if (currentTodos.isNotEmpty()) {
                items(currentTodos) { todo ->
                    ListTile(viewModel, todo = todo)
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
                            text = stringResource(id = week[currentDay + (i - currentDay)]) + " is Empty!",
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