package com.melihcan.todoapp.presentation.features.main.components

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.melihcan.todoapp.R
import com.melihcan.todoapp.extensions.SetTheme
import com.melihcan.todoapp.presentation.features.main.HomePageAction
import com.melihcan.todoapp.presentation.features.main.HomePageViewModel
import com.melihcan.todoapp.presentation.theme.TodoTypo
import com.melihcan.todoapp.storage.SharedPrefManager


@Composable
fun SettingsPanel(
    viewModel: HomePageViewModel,
    setTheme: SetTheme
) {
    val ctx = LocalContext.current

    val sharedP = SharedPrefManager.getInstance(ctx)

    var isDarkTheme by remember {
        mutableStateOf(sharedP.isDarkTheme)
    }

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.settings),
                style = TodoTypo.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Divider(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f))
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {}
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "theme",
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(id = R.string.darkTheme),
                        style = TodoTypo.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Switch(checked = isDarkTheme, onCheckedChange = {
                    isDarkTheme = !isDarkTheme
                    setTheme.commit(setTheme.state.value.copy(isDarkTheme = isDarkTheme))
                    sharedP.isDarkTheme = !sharedP.isDarkTheme
                })
            }
            Divider(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f))
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            viewModel.dispatch(HomePageAction.Logout)
                        }
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ExitToApp,
                        contentDescription = "Logout",
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(id = R.string.logout),
                        style = TodoTypo.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
            Divider(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f))
        }
    }
}
