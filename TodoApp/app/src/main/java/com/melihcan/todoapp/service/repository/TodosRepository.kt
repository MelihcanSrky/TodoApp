package com.melihcan.todoapp.service.repository

import android.content.Context
import com.melihcan.todoapp.service.ServiceInstance
import com.melihcan.todoapp.storage.SharedPrefManager
import javax.inject.Inject

class TodosRepository @Inject constructor(
    private val serviceInstance: ServiceInstance,
    context: Context
) {

    val token = SharedPrefManager.getInstance(context).token.token
    val username = "Melihcan"
    val useruuid = "a0364432-1c94-454e-b9a7-aef4547fe6fb"

    suspend fun getTodos(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        try {
            val response =
                serviceInstance.getTodos(token = token, username = username, useruuid = useruuid)
            if (response.isSuccessful) {
                val res = response.body()
                res?.let { onSuccess()}
            } else {
                onFailure()
            }
        } catch (e: Exception) {
            onFailure()
        }
    }
}