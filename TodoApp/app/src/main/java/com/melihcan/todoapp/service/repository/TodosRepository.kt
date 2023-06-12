package com.melihcan.todoapp.service.repository

import android.content.Context
import com.melihcan.todoapp.model.TodosModel
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
                res?.let { onSuccess(it)}
            } else {
                onFailure()
            }
        } catch (e: Exception) {
            onFailure()
        }
    }
}