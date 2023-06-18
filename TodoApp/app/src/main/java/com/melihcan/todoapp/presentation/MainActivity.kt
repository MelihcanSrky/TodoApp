package com.melihcan.todoapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.melihcan.todoapp.extensions.SetTheme
import com.melihcan.todoapp.presentation.navigation.Navigator
import com.melihcan.todoapp.presentation.theme.TodoAppTheme
import com.melihcan.todoapp.storage.SharedPrefManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedP = SharedPrefManager.getInstance(this)

        setContent {
            if (sharedP.readSystemTheme == "null") {
                sharedP.isDarkTheme = isSystemInDarkTheme()
                sharedP.readSystemTheme = "readed"
            }

            val setThemeVM = SetTheme(this)


            TodoAppTheme(
                darkTheme = setThemeVM.state.value.isDarkTheme
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigator(this, setThemeVM)
                }
            }
        }
    }
}