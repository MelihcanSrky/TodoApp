package com.melihcan.todoapp.service.repository

import android.content.Context
import com.melihcan.todoapp.model.CreateTodoModel
import com.melihcan.todoapp.model.TodosModel
import com.melihcan.todoapp.model.UpdateTodoModel
import com.melihcan.todoapp.service.ServiceInstance
import com.melihcan.todoapp.storage.SharedPrefManager
import javax.inject.Inject

class TodosRepository @Inject constructor(
    private val serviceInstance: ServiceInstance,
    context: Context
) {

    val token = SharedPrefManager.getInstance(context).token.token
    val username = SharedPrefManager.getInstance(context).user.username
    val useruuid = SharedPrefManager.getInstance(context).user.uuid

    suspend fun getTodos(
        onSuccess: (List<TodosModel>) -> Unit,
        onFailure: () -> Unit
    ) {
        try {
            val response =
                serviceInstance.getTodos(token = token, username = username, useruuid = useruuid)
            if (response.isSuccessful) {
                val res = response.body()
                res?.let { onSuccess(it) }
            } else {
                onFailure()
            }
        } catch (e: Exception) {
            onFailure()
        }
    }

    suspend fun createTodo(
        body: CreateTodoModel,
        onSuccess: (Int) -> Unit,
        onFailure: () -> Unit
    ) {
        var editBody = body
        editBody.useruuid = useruuid
        try {
            val response = serviceInstance.createTodo(
                token = token,
                username = username,
                useruuid = useruuid,
                body = editBody
            )
            if (response.isSuccessful) {
                val res = response.code()
                onSuccess(res)
            } else {
                onFailure()
            }
        } catch (e: Exception) {
            onFailure()
        }
    }

    suspend fun updateTodo(
        uuid: String,
        body: UpdateTodoModel,
        onSuccess: (Boolean) -> Unit,
        onFailure: () -> Unit
    ) {
        try {
            val response = serviceInstance.updateTodo(
                token = token,
                username = username,
                uuid = uuid,
                body = body
            )
            if (response.isSuccessful) {
                val res = response.body()
                res?.let { onSuccess(res.value) }
            } else {
                onFailure()
            }
        } catch (e: Exception) {
            onFailure()
        }
    }
}