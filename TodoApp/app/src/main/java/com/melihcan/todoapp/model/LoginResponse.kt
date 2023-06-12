package com.melihcan.todoapp.model

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    @SerializedName("token")
    val token: String,
)