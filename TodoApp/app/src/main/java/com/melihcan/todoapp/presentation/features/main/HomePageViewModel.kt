package com.melihcan.todoapp.presentation.features.main

import androidx.lifecycle.viewModelScope
import com.melihcan.todoapp.model.TodosModel
import com.melihcan.todoapp.presentation.core.BaseViewModel
import com.melihcan.todoapp.presentation.core.ViewAction
import com.melihcan.todoapp.presentation.core.ViewEffect
import com.melihcan.todoapp.presentation.core.ViewState
import com.melihcan.todoapp.presentation.features.auth.shared.IsSuccess
import com.melihcan.todoapp.service.repository.TodosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomePageAction : ViewAction {
    object GetTodos : HomePageAction()
}

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val todosRepository: TodosRepository
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
    ) : ViewState

    private fun getTodos() {
        viewModelScope.launch {
            try {
                commit(state.value.copy(isSuccess = IsSuccess.LOADING))
                todosRepository.getTodos(
                    onSuccess = { list ->
                        println(list)
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

    override fun dispatch(action: HomePageAction) {
        when (action) {
            is HomePageAction.GetTodos -> getTodos()
        }
    }

    sealed class Effect : ViewEffect
}