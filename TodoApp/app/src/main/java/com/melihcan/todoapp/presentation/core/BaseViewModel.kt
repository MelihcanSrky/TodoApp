package com.melihcan.todoapp.presentation.core

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface ViewState
interface ViewAction
interface ViewEffect

abstract class BaseViewModel<S: ViewState, A: ViewAction, E: ViewEffect>(
    initialState: S
) : ViewModel() {
    private val _state = mutableStateOf(initialState)
    val state: State<S> = _state

    private val _sideEffect = MutableSharedFlow<E>()
    val sideEffect = _sideEffect.asSharedFlow()

    abstract fun dispatch(action: A)

    fun commit(newState: S) {
        if (newState != _state.value) {
            _state.value = newState
        }
    }

    fun updateSideEffect(effect: E) {
        viewModelScope.launch { _sideEffect.emit(effect) }
    }
}