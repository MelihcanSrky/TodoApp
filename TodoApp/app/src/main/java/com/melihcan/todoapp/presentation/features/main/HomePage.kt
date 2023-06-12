package com.melihcan.todoapp.presentation.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.melihcan.todoapp.extensions.getCurrentDayOfWeek
import com.melihcan.todoapp.extensions.getCurrentWeekOfYear
import com.melihcan.todoapp.extensions.getFirstDayOfWeek
import com.melihcan.todoapp.model.WeekModel
import com.melihcan.todoapp.model.week
import com.melihcan.todoapp.presentation.theme.TodoTypo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    viewModel: HomePageViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    Scaffold(
        topBar = {
            buildTabBar()
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(state.todos) { todo ->
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
                                modifier = Modifier.size(48.dp)
                                    .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp)
                                    .background(MaterialTheme.colorScheme.onBackground)
                                    .border(0.dp, Color.Transparent, RoundedCornerShape(6.dp))
                            ) {
                                Checkbox(
                                    modifier = Modifier
                                        .padding(0.dp)
                                        .size(24.dp)
                                        .background(Color.Transparent),
                                    checked = true,
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
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun buildTabBar() {
    val currentDay = getCurrentDayOfWeek()
    val currentWeek = getCurrentWeekOfYear()
    val firstDayOfWeek = getFirstDayOfWeek(currentWeek)
    println(currentDay.toString() + " " + currentWeek.toString() + " " + firstDayOfWeek.toString())
    var weekDays = mutableListOf<WeekModel>()
    for (i in 0..6) {
        weekDays.add(WeekModel(i + firstDayOfWeek, week[i]))
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
                Text(text = "Good Morning", style = TodoTypo.headlineSmall)
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