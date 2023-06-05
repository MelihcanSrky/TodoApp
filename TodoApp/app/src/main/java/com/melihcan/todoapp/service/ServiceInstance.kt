package com.melihcan.todoapp.service

import com.melihcan.todoapp.model.LoginResponseModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ServiceInstance {
    @FormUrlEncoded
    @POST("user/login")
    suspend fun loginUser(
    @Field("username") username: String,
    @Field("password") password: String
    ) : Response<LoginResponseModel>
}