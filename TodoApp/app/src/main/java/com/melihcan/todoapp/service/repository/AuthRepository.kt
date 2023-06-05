package com.melihcan.todoapp.service.repository

import android.content.Context
import com.melihcan.todoapp.model.ErrorModel
import com.melihcan.todoapp.model.LoginResponseModel
import com.melihcan.todoapp.service.ServiceInstance
import com.melihcan.todoapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import java.lang.Error
import javax.inject.Inject

open class AuthRepository @Inject constructor(
    private val serviceInstance: ServiceInstance,
    context: Context
) {
    private val loginChannelData = Channel<Resource<LoginResponseModel, ErrorModel>> {  }
    val loginFlowData: Flow<Resource<LoginResponseModel, ErrorModel>> = loginChannelData.receiveAsFlow()

    suspend fun loginUser(
        username: String,
        password: String
    ) {
        withContext(Dispatchers.IO) {
            val res = serviceInstance.loginUser(username, password)
            if (res.isSuccessful && res.body() != null) {
                loginChannelData.send(Resource.Data(res.body()!!))
            } else {
                loginChannelData.send(Resource.Error(ErrorModel(res.message())))
            }
        }
    }
}