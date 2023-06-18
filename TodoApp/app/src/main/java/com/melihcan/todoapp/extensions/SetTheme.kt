package com.melihcan.todoapp.extensions

import android.content.Context
import androidx.lifecycle.ViewModel
import com.melihcan.todoapp.presentation.core.BaseViewModel
import com.melihcan.todoapp.presentation.core.ViewAction
import com.melihcan.todoapp.presentation.core.ViewEffect
import com.melihcan.todoapp.presentation.core.ViewState
import com.melihcan.todoapp.presentation.features.main.HomePageAction
import com.melihcan.todoapp.presentation.features.main.HomePageViewModel
import com.melihcan.todoapp.storage.SharedPrefManager
import com.melihcan.todoapp.utils.IsSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class SetThemeAction : ViewAction

@HiltViewModel
class SetTheme @Inject constructor(
    private val context: Context
) : BaseViewModel<SetTheme.State, SetThemeAction, SetTheme.Effect>(
    initialState = SetTheme.State(
        isDarkTheme = SharedPrefManager.getInstance(context).isDarkTheme
    )
) {
    data class State(
        val isDarkTheme: Boolean
    ) : ViewState


    override fun dispatch(action: SetThemeAction) {

    }

    sealed class Effect : ViewEffect
}