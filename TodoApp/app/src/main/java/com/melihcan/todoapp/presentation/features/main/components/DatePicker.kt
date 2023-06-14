package com.melihcan.todoapp.presentation.features.main.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import com.melihcan.todoapp.extensions.getSelectedDate
import com.melihcan.todoapp.extensions.getSelectedDayOfYear
import com.melihcan.todoapp.extensions.getSelectedWeekOfYear
import com.melihcan.todoapp.presentation.features.main.HomePageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    viewModel: HomePageViewModel
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
            navigationContentColor = MaterialTheme.colorScheme.primary
        ),
        onDismissRequest = {
            viewModel.commit(viewModel.state.value.copy(dateDialog = false))
        },
        confirmButton = {
            TextButton(
                enabled = (datePickerState.selectedDateMillis != null),
                onClick = {
                viewModel.commit(viewModel.state.value.copy(
                    weekOfYear = getSelectedWeekOfYear(datePickerState.selectedDateMillis!!),
                    dayOfWeek = getSelectedDayOfYear(datePickerState.selectedDateMillis!!),
                    selectedDate = getSelectedDate(datePickerState.selectedDateMillis!!),
                    dateDialog = false
                ))
            }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                viewModel.commit(viewModel.state.value.copy(
                    dateDialog = false
                ))
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            colors = DatePickerDefaults.colors(
                weekdayContentColor = MaterialTheme.colorScheme.onSecondary,
                dayContentColor = MaterialTheme.colorScheme.onSecondary
            ),
            state = datePickerState
        )
    }
}