package com.melihcan.todoapp.service

import com.melihcan.todoapp.model.LoginRequestModel
import com.melihcan.todoapp.model.LoginResponseModel
import retrofit2.Response
import retrofit2.http.*

interface ServiceInstance {
    @POST("user/login")
    suspend fun loginUser(
    @Body body: LoginRequestModel
    ) : Response<LoginResponseModel>
}