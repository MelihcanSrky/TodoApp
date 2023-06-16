package com.melihcan.todoapp.presentation.features.main.components

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.melihcan.todoapp.presentation.features.main.HomePageAction
import com.melihcan.todoapp.presentation.features.main.HomePageViewModel
import com.melihcan.todoapp.presentation.features.main.SheetPanel
import com.melihcan.todoapp.presentation.theme.TodoTypo
import com.melihcan.todoapp.storage.SharedPrefManager

@Composable
fun CreateTaskPanel(
    focusRequester: FocusRequester,
    viewModel: HomePageViewModel,
) {
    val ctx = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }
    val sharedP = SharedPrefManager.getInstance(ctx)
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
                CustomIconButton(
                    icon = Icons.Outlined.DateRange,
                    text = viewModel.state.value.selectedDate,
                    onClick = {
                        viewModel.commit(viewModel.state.value.copy(dateDialog = true))
                    })
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    DropdownMenu(
                        expanded = expanded, onDismissRequest = { expanded = false }) {
                        sharedP.lists.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.background
                                ),
                                text = {
                                    Text(
                                        text = item.name,
                                        color = MaterialTheme.colorScheme.background
                                    )
                                }, onClick = {
                                    viewModel.commit(viewModel.state.value.copy(category = item.id))
                                    expanded = false
                                })
                        }
                    }
                    CustomIconButton(
                        icon = Icons.Outlined.List,
                        text = sharedP.lists.find { it.id == viewModel.state.value.category }?.name
                            ?: "No List",
                        onClick = { expanded = true })
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
                    viewModel.commit(viewModel.state.value.copy(sheetPanel = SheetPanel.NONE))
                }) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "Send")
            }
        }
    }
}

@Composable
fun CustomIconButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
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
        onClick = onClick
    ) {
        Icon(imageVector = icon, contentDescription = text)
        Text(text = text, style = TodoTypo.bodyMedium.copy(fontSize = 12.sp))
    }
}