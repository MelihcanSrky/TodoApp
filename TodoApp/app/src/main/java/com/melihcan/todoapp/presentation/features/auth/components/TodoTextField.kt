package com.melihcan.todoapp.presentation.features.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.melihcan.todoapp.presentation.theme.MCSRadiusMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape = MCSRadiusMedium,
        isError = isError,
        label = {
            Text(text = label)
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorLabelColor = MaterialTheme.colorScheme.error,
            textColor = MaterialTheme.colorScheme.surface
        )
    )
}