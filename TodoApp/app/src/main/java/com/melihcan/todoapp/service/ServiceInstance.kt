package com.melihcan.todoapp.service

import com.melihcan.todoapp.model.LoginRequestModel
import com.melihcan.todoapp.model.LoginResponseModel
import com.melihcan.todoapp.model.RegisterResponseModel
import com.melihcan.todoapp.model.TodosModel
import retrofit2.Response
import retrofit2.http.*

interface ServiceInstance {
    @POST("user/login")
    suspend fun loginUser(
    @Body body: LoginRequestModel
    ) : Response<LoginResponseModel>

    @POST("user")
    suspend fun registerUser(
        @Body body: LoginRequestModel
    ) : Response<RegisterResponseModel>

    @GET("todos/{username}/{useruuid}")
    suspend fun getTodos(
        @Header("X-jwt-Token") token: String,
        @Path("username") username: String,
        @Path("useruuid") useruuid: String,
    ) : Response<List<TodosModel>>
}