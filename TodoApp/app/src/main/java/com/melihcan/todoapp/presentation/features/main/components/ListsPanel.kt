package com.melihcan.todoapp.presentation.features.main.components

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.melihcan.todoapp.extensions.getCurrentDayOfWeek
import com.melihcan.todoapp.extensions.getCurrentWeekOfYear
import com.melihcan.todoapp.model.ListModel
import com.melihcan.todoapp.presentation.features.auth.components.TodoTextField
import com.melihcan.todoapp.presentation.features.main.HomePageViewModel
import com.melihcan.todoapp.presentation.features.main.SheetPanel
import com.melihcan.todoapp.presentation.theme.TodoTypo
import com.melihcan.todoapp.storage.SharedPrefManager
import kotlinx.coroutines.MainScope

@Composable
fun ListsPanel(
    viewModel: HomePageViewModel
) {
    val ctx = LocalContext.current
    var listNameValue by remember {
        mutableStateOf("")
    }
    var listNameEdit by remember {
        mutableStateOf(false)
    }

    val sharedP = SharedPrefManager.getInstance(ctx)

    val currentTodos =
        viewModel.state.value.todos.filter { it.weekOfYear == getCurrentWeekOfYear() }

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Lists",
                style = TodoTypo.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Divider(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f))
        }

        items(sharedP.lists) { list ->
            ListItem(
                listItem = list,
                taskCount = currentTodos.filter { it.category == list.id && it.dayOfWeek >= getCurrentDayOfWeek() }.size.toString() + " tasks"
            )
        }

        item {
            ListItem(taskCount = currentTodos.filter { it.category == "00000" && it.dayOfWeek >= getCurrentDayOfWeek() }.size.toString() + " tasks")
        }

        if (listNameEdit == false)
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                listNameEdit = true
                            }
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Add list",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "New list",
                            style = TodoTypo.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Divider(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f))
            }

        if (listNameEdit == true)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextField(
                        value = listNameValue,
                        onValueChange = { value ->
                            listNameValue = value
                        },
                        textStyle = TodoTypo.bodyMedium.copy(color = MaterialTheme.colorScheme.surface),
                        placeholder = {
                            Text(
                                text = " Give List a Name",
                                style = TodoTypo.bodyMedium,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        modifier = Modifier
                            .padding(0.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            cursorColor = MaterialTheme.colorScheme.surface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                    )
                    IconButton(onClick = {
                        val temp = sharedP.lists.toMutableList()
                        val newList = ListModel(name = listNameValue)
                        temp.add(newList)
                        sharedP.saveList(temp)
                        viewModel.commit(viewModel.state.value.copy(sheetPanel = SheetPanel.NONE))
                        listNameEdit = false
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = "Add List",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = {
                        listNameEdit = false
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Cancel",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
                Divider(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f))
            }
    }
}

@Composable
fun ListItem(
    listItem: ListModel? = null,
    taskCount: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.List,
                contentDescription = listItem?.id ?: "No List",
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = listItem?.name ?: "No List",
                style = TodoTypo.bodyMedium,
                color = MaterialTheme.colorScheme.surface
            )
        }
        Text(
            text = taskCount,
            style = TodoTypo.bodySmall,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
    Divider(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f))
}