package com.melihcan.todoapp.presentation.features.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.melihcan.todoapp.R
import com.melihcan.todoapp.model.WeekModel
import com.melihcan.todoapp.model.week
import com.melihcan.todoapp.presentation.features.main.HomePageAction
import com.melihcan.todoapp.presentation.features.main.HomePageViewModel
import com.melihcan.todoapp.presentation.theme.TodoTypo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabBar(
    currentDay: Int,
    firstDayOfWeek: Int,
    viewModel: HomePageViewModel
) {
    var weekDays = mutableListOf<WeekModel>()
    for (i in 0..6) {
        weekDays.add(WeekModel(i + firstDayOfWeek, stringResource(id = week[i])))
    }

    var selectedIndex by remember { mutableStateOf(currentDay) }

    Column(
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.surface
            ),
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(id = R.string.welcome_back), style = TodoTypo.headlineSmall)
                }
            }
        )
        TabRow(
            selectedTabIndex = selectedIndex,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            weekDays.forEachIndexed { index, day ->
                Tab(
                    unselectedContentColor = if (index >= currentDay) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSecondary,
                    selectedContentColor = if (index >= currentDay) MaterialTheme.colorScheme.primary else Color.Transparent,
                    selected = selectedIndex == index,
                    onClick = {
                        if (index < currentDay) {
                            null
                        } else {
                            selectedIndex = index
                        }
                    },
                    text = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("${day.dayDate}", style = TodoTypo.titleMedium)
                            Text(
                                day.dayName.take(3),
                                style = TodoTypo.bodySmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    })
            }
        }
    }
}