package com.melihcan.todoapp.presentation.features.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginAction : ViewAction {
    data class UpdateUsername(val username: String) : LoginAction()
    data class UpdatePassword(val password: String) : LoginAction()
    data class UpdateButton(val isButtonEnabled: Boolean) : LoginAction()
    object ResetState : LoginAction()
    object LoginUser : LoginAction()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<LoginViewModel.State, LoginAction, LoginViewModel.Effect>(
    initialState = State(
        username = "",
        password = "",
        token = null,
        isSuccess = IsSuccess.NONE,
        isButtonEnabled = false
    )
) {

    data class State(
        val username: String,
        val password: String,
        val token: String?,
        val isSuccess: IsSuccess,
        val isButtonEnabled: Boolean
    ) : ViewState


    fun resetState() {
        commit(State("", "", "", IsSuccess.NONE, false))
    }

    fun buttonState() {
        if (state.value.username.isUsernameValid() && state.value.password.isPasswordValid()) {
            dispatch(LoginAction.UpdateButton(true))
        } else {
            dispatch(LoginAction.UpdateButton(false))
        }
    }

    private fun loginUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                commit(state.value.copy(isSuccess = IsSuccess.LOADING, isButtonEnabled = false))
                authRepository.loginUser(
                    LoginRequestModel(
                        username = state.value.username,
                        password = state.value.password
                    ), onSuccess = { token ->
                        commit(
                            state.value.copy(
                                token = token,
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

    override fun dispatch(action: LoginAction) {
        when (action) {
            is LoginAction.UpdateUsername -> {
                commit(state.value.copy(username = action.username))
                buttonState()
            }

            is LoginAction.UpdatePassword -> {
                commit(state.value.copy(password = action.password))
                buttonState()
            }

            is LoginAction.UpdateButton -> commit(state.value.copy(isButtonEnabled = action.isButtonEnabled))
            is LoginAction.ResetState -> resetState()
            is LoginAction.LoginUser -> loginUser()
        }
    }

    sealed class Effect : ViewEffect
}