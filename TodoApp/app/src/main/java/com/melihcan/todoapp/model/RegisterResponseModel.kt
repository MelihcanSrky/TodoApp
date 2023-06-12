package com.melihcan.todoapp.model

import com.google.gson.annotations.SerializedName

data class RegisterResponseModel(
    @SerializedName("uuid")
    val uuid: String,
)