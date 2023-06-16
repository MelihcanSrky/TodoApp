package com.melihcan.todoapp.presentation.features.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.melihcan.todoapp.model.TodosModel
import com.melihcan.todoapp.presentation.features.main.HomePageAction
import com.melihcan.todoapp.presentation.features.main.HomePageViewModel
import com.melihcan.todoapp.presentation.theme.TodoTypo
import com.melihcan.todoapp.storage.SharedPrefManager

@Composable
fun ListTile(viewModel: HomePageViewModel, todo: TodosModel) {
    val ctx = LocalContext.current
    val sharedP = SharedPrefManager.getInstance(ctx)
    val categoryLists = sharedP.getLists(sharedP.user.uuid)
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
                    onCheckedChange = {
                        viewModel.dispatch(HomePageAction.UpdateTodo(!todo.isChecked, todo.uuid))
                    },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.Transparent,
                        checkedColor = Color.Transparent,
                        checkmarkColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
            Text(
                text = todo.title,
                style = TodoTypo.bodyMedium.copy(
                    textDecoration = if (todo.isChecked) TextDecoration.LineThrough else TextDecoration.None
                ),
                color = MaterialTheme.colorScheme.surface
            )
        }
        Text(
            text = if (todo.category == "00000") "No List" else (categoryLists.find { it.id == todo.category }?.name ?: "Not Found!"),
            style = TodoTypo.bodySmall,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
    Divider(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f))
}