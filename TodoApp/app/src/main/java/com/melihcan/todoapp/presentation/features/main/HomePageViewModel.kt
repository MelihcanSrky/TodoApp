package com.melihcan.todoapp.presentation.features.main

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.melihcan.todoapp.extensions.getCurrentDate
import com.melihcan.todoapp.model.CreateTodoModel
import com.melihcan.todoapp.model.TodosModel
import com.melihcan.todoapp.model.UpdateTodoModel
import com.melihcan.todoapp.presentation.core.BaseViewModel
import com.melihcan.todoapp.presentation.core.ViewAction
import com.melihcan.todoapp.presentation.core.ViewEffect
import com.melihcan.todoapp.presentation.core.ViewState
import com.melihcan.todoapp.utils.IsSuccess
import com.melihcan.todoapp.service.repository.TodosRepository
import com.melihcan.todoapp.storage.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

sealed class HomePageAction : ViewAction {
    data class CreateTodo(val weekOfYear: Int, val dayOfWeek: Int) : HomePageAction()
    data class UpdateTodo(val value: Boolean, val uuid: String) : HomePageAction()
    object GetTodos : HomePageAction()
    object Logout : HomePageAction()
}

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val todosRepository: TodosRepository,
    private val context: Context
) : BaseViewModel<HomePageViewModel.State, HomePageAction, HomePageViewModel.Effect>(
    initialState = State(
        isSuccess = IsSuccess.NONE
    )
) {

    init {
        getTodos()
    }

    data class State(
        val todos: List<TodosModel> = emptyList(),
        val isSuccess: IsSuccess,
        val isLogin: Boolean = true,
        val isTodoAdded: Boolean = false,
        val title: String = "",
        val currentDate: String = getCurrentDate(),
    ) : ViewState

    private fun getTodos() {
        viewModelScope.launch {
            try {
                commit(state.value.copy(isSuccess = IsSuccess.LOADING))
                todosRepository.getTodos(
                    onSuccess = { list ->
                        commit(state.value.copy(isSuccess = IsSuccess.SUCCESS, todos = list))
                    },
                    onFailure = {
                        commit(
                            state.value.copy(
                                isSuccess = IsSuccess.ERROR
                            )
                        )
                    }
                )
            } catch (e: Exception) {
                commit(state.value.copy(isSuccess = IsSuccess.ERROR))
                e.printStackTrace()
            }
        }
    }

    private fun createTodo(
        weekOfYear: Int,
        dayOfWeek: Int
    ) {
        viewModelScope.launch {
            try {
                commit(state.value.copy(isSuccess = IsSuccess.LOADING))
                todosRepository.createTodo(
                    body = CreateTodoModel(
                        useruuid = "",
                        title = state.value.title.toString(),
                        weekOfYear = weekOfYear,
                        dayOfWeek = dayOfWeek,
                        assignedAt = state.value.currentDate
                    ),
                    onSuccess = {
                        commit(state.value.copy(isSuccess = IsSuccess.SUCCESS, isTodoAdded = true))
                        getTodos()
                    },
                    onFailure = {
                        commit(state.value.copy(isSuccess = IsSuccess.ERROR))
                    }
                )
            } catch (e: Exception) {
                commit(state.value.copy(isSuccess = IsSuccess.ERROR))
                e.printStackTrace()
            }
        }
    }

    private fun updateTodo(
        value: Boolean,
        uuid: String
    ) {
        viewModelScope.launch {
            try {
                todosRepository.updateTodo(
                    body = UpdateTodoModel(value = value),
                    uuid = uuid,
                    onSuccess = {
                        getTodos()
                    },
                    onFailure = {

                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun logout(): Boolean {
        SharedPrefManager.getInstance(context).clear()
        commit(state.value.copy(isLogin = false))
        return true
    }

    override fun dispatch(action: HomePageAction) {
        when (action) {
            is HomePageAction.GetTodos -> getTodos()
            is HomePageAction.Logout -> logout()
            is HomePageAction.CreateTodo -> createTodo(action.weekOfYear, action.dayOfWeek)
            is HomePageAction.UpdateTodo -> updateTodo(action.value, action.uuid)
            else -> {}
        }
    }

    sealed class Effect : ViewEffect
}