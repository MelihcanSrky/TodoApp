package com.melihcan.todoapp.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melihcan.todoapp.model.LoginRequestModel
import com.melihcan.todoapp.service.ServiceInstance
import com.melihcan.todoapp.service.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

data class State(
    var username: String = "",
    var password: String = "",
    var token: String = ""
)

val getToken = { state: State -> state.token }
val getUsername = { state: State -> state.username }
val getPassword = { state: State -> state.password }

val setToken = { state: State, payload: String -> state.token = payload }
val setUsername = { state: State, payload: String -> state.username = payload }
val setPassword = { state: State, payload: String -> state.password = payload }

@HiltViewModel
class LoginPageStore @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val state = State()

    // Getters
    fun getters(getter: String): String? {
        return when (getter) {
            "getToken" -> getToken(state)
            "getUsername" -> getUsername(state)
            "getPassword" -> getPassword(state)
            else -> null
        }
    }

    // Mutations
    fun setToken(payload: String) {
        setToken(state, payload)
    }

    fun setUsername(payload: String) {
        setUsername(state, payload)
    }

    fun setPassword(payload: String) {
        setPassword(state, payload)
    }

    // Actions
    fun loginUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                authRepository.loginUser(LoginRequestModel(
                    username = state.username,
                    password = state.password
                ), onSuccess = { token ->
                    commit("setToken", token)
                }, onFailure = {
                    commit("setToken", "")
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Commits
    private fun commit(type: String, payload: String) {
        when (type) {
            "setToken" -> setToken(payload)
            "setUsername" -> setUsername(payload)
            "setPassword" -> setPassword(payload)
        }
    }
}
