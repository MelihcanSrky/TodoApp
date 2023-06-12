package com.melihcan.todoapp.model

import com.google.gson.annotations.SerializedName

data class TodoModel(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("user_uuid")
    val user_uuid: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("last_at")
    val lastAt: Int,
    @SerializedName("is_checked")
    val isChecked: Boolean,
    @SerializedName("category")
    val category: String,
    @SerializedName("priority")
    val priority: Int,
    @SerializedName("assigned_at")
    val assignedAt: Int,
)