package com.melihcan.todoapp.model

import com.google.gson.annotations.SerializedName

data class UpdateTodoModel(
    @SerializedName("value")
    val value: Boolean
)
