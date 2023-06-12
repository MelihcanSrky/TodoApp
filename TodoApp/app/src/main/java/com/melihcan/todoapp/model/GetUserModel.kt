package com.melihcan.todoapp.model

import com.google.gson.annotations.SerializedName

data class GetUserModel(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("username")
    val username: String
)