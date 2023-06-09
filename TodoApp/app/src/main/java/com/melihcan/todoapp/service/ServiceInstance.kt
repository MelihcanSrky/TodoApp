package com.melihcan.todoapp.service

import com.melihcan.todoapp.model.CreateTodoModel
import com.melihcan.todoapp.model.GetUserModel
import com.melihcan.todoapp.model.LoginRequestModel
import com.melihcan.todoapp.model.LoginResponseModel
import com.melihcan.todoapp.model.RegisterResponseModel
import com.melihcan.todoapp.model.TodosModel
import com.melihcan.todoapp.model.UpdateTodoModel
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

    @GET("user/{username}")
    suspend fun getUserByUsername(
        @Header("X-jwt-Token") token: String,
        @Path("username") username: String
    ) : Response<GetUserModel>

    @GET("todos/{username}/{useruuid}")
    suspend fun getTodos(
        @Header("X-jwt-Token") token: String,
        @Path("username") username: String,
        @Path("useruuid") useruuid: String,
    ) : Response<List<TodosModel>>

    @POST("todos/{username}/{useruuid}")
    suspend fun createTodo(
        @Header("X-jwt-Token") token: String,
        @Path("username") username: String,
        @Path("useruuid") useruuid: String,
        @Body body: CreateTodoModel
    ) : Response<TodosModel>

    @PUT("todos/update/{username}/{uuid}")
    suspend fun updateTodo(
        @Header("X-jwt-Token") token: String,
        @Path("username") username: String,
        @Path("uuid") uuid: String,
        @Body body: UpdateTodoModel
    ) : Response<UpdateTodoModel>
}