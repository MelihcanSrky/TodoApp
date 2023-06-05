package com.melihcan.todoapp.model

import com.google.gson.annotations.SerializedName

data class LoginRequestModel(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password:String,
)