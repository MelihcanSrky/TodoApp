package com.melihcan.todoapp.service.repository

import android.content.Context
import android.util.Log
import com.melihcan.todoapp.di.RetrofitClient
import com.melihcan.todoapp.model.ErrorModel
import com.melihcan.todoapp.model.GetUserModel
import com.melihcan.todoapp.model.LoginRequestModel
import com.melihcan.todoapp.model.LoginResponseModel
import com.melihcan.todoapp.service.ServiceInstance
import com.melihcan.todoapp.storage.SharedPrefManager
import com.melihcan.todoapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val serviceInstance: ServiceInstance,
    context: Context
    ) {

    val token = SharedPrefManager.getInstance(context).token.token

    suspend fun loginUser(
        user: LoginRequestModel,
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit
    ) {
        try {
            val response = serviceInstance.loginUser(user)
            if (response.isSuccessful) {
                val res = response.body()
                res?.let { onSuccess(it.token) }
            } else {
                onFailure()
            }
        } catch (e: Exception) {
            onFailure()
        }
    }

    suspend fun registerUser(
        user: LoginRequestModel,
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit
    ) {
        try {
            val response = serviceInstance.registerUser(user)
            if (response.isSuccessful) {
                val res = response.body()
                res?.let { onSuccess(it.uuid) }
            } else {
                onFailure()
            }
        } catch (e: Exception) {
            onFailure()
        }
    }

    suspend fun getUserByUsername(
        username: String,
        token: String,
        onSuccess: (GetUserModel) -> Unit,
        onFailure: () -> Unit
    ) {
        try {
            val response = serviceInstance.getUserByUsername(token = token, username = username)
            if (response.isSuccessful) {
                val res = response.body()
                res?.let {onSuccess(it)}
            } else {
                onFailure()
            }
        } catch (e: Exception) {
            onFailure()
        }
    }
}