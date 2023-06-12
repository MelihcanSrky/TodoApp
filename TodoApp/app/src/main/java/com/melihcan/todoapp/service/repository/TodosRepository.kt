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

    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6Ik1lbGloY2FuIn0.n_TjZHrPtrpfqgp5cRFkXkw62j2XAW0Bl6knH7ig3iY"// SharedPrefManager.getInstance(context).token.token
    val username = "Melihcan"
    val useruuid = "a0364432-1c94-454e-b9a7-aef4547fe6fb"

    suspend fun getTodos(
        onSuccess: (List<TodosModel>) -> Unit,
        onFailure: () -> Unit
    ) {
        try {
            println("Start Process in repo")
            val response =
                serviceInstance.getTodos(token = token, username = username, useruuid = useruuid)
            if (response.isSuccessful) {
                println("Is Success")
                val res = response.body()
                res?.let { onSuccess(it)}
            } else {
                println("Is Success")
                onFailure()
            }
        } catch (e: Exception) {
            println("Is Exception")
            onFailure()
        }
    }
}