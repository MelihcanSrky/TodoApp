package com.melihcan.todoapp.presentation.features.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.melihcan.todoapp.presentation.theme.MCSRadiusMedium
import com.melihcan.todoapp.presentation.theme.TodoTypo

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
        singleLine = true,
        textStyle = TodoTypo.bodyMedium.copy(color = MaterialTheme.colorScheme.surface),
        label = {
            Text(text = label, style = TodoTypo.bodySmall.copy(fontSize = 12.sp))
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorLabelColor = MaterialTheme.colorScheme.error,
        )
    )
}