package com.melihcan.todoapp.presentation.features.auth

import androidx.lifecycle.viewModelScope
import com.melihcan.todoapp.extensions.isPasswordValid
import com.melihcan.todoapp.extensions.isUsernameValid
import com.melihcan.todoapp.model.LoginRequestModel
import com.melihcan.todoapp.presentation.core.BaseViewModel
import com.melihcan.todoapp.presentation.core.ViewAction
import com.melihcan.todoapp.presentation.core.ViewEffect
import com.melihcan.todoapp.presentation.core.ViewState
import com.melihcan.todoapp.presentation.features.auth.shared.IsSuccess
import com.melihcan.todoapp.service.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RegisterAction : ViewAction {
    data class UpdateUsername(val username: String) : RegisterAction()
    data class UpdatePassword(val password: String) : RegisterAction()
    data class UpdateConfirmPassword(val password: String) : RegisterAction()
    data class UpdateButton(val isButtonEnabled: Boolean) : RegisterAction()
    object ResetState : RegisterAction()
    object RegisterUser : RegisterAction()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<RegisterViewModel.State, RegisterAction, RegisterViewModel.Effect>(
    initialState = State(
        username = "",
        password = "",
        confirmPassword = "",
        isSuccess = IsSuccess.NONE,
        isButtonEnabled = false
    )
) {
    data class State(
        val username: String,
        val password: String,
        val confirmPassword: String,
        val isSuccess: IsSuccess,
        val isButtonEnabled: Boolean
    ) : ViewState

    fun resetState() {
        commit(RegisterViewModel.State("", "", "", IsSuccess.NONE, false))
    }

    fun buttonState() {
        if (
            state.value.username.isUsernameValid() &&
            state.value.password.isPasswordValid() &&
            state.value.password == state.value.confirmPassword
        ) {
            dispatch(RegisterAction.UpdateButton(true))
        } else {
            dispatch(RegisterAction.UpdateButton(false))
        }
    }

    private fun registerUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                commit(state.value.copy(isSuccess = IsSuccess.LOADING, isButtonEnabled = false))
                authRepository.registerUser(
                    LoginRequestModel(
                        username = state.value.username,
                        password = state.value.password
                    ), onSuccess = { token ->
                        commit(
                            state.value.copy(
                                isSuccess = IsSuccess.SUCCESS,
                                isButtonEnabled = true
                            )
                        )
                    }, onFailure = {
                        commit(
                            state.value.copy(
                                isSuccess = IsSuccess.ERROR,
                                isButtonEnabled = true
                            )
                        )
                    })
            } catch (e: Exception) {
                commit(state.value.copy(isSuccess = IsSuccess.ERROR, isButtonEnabled = true))
                e.printStackTrace()
            }
        }
    }

    override fun dispatch(action: RegisterAction) {
        when (action) {
            is RegisterAction.UpdateUsername -> {
                commit(state.value.copy(username = action.username))
                buttonState()
            }

            is RegisterAction.UpdatePassword -> {
                commit(state.value.copy(password = action.password))
                buttonState()
            }

            is RegisterAction.UpdateConfirmPassword -> {
                commit(state.value.copy(confirmPassword = action.password))
                buttonState()
            }

            is RegisterAction.UpdateButton -> commit(state.value.copy(isButtonEnabled = action.isButtonEnabled))
            is RegisterAction.ResetState -> resetState()
            is RegisterAction.RegisterUser -> registerUser()
        }
    }

    sealed class Effect : ViewEffect
}