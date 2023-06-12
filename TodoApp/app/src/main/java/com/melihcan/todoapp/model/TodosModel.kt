package com.melihcan.todoapp.model

import com.google.gson.annotations.SerializedName

data class TodosModel(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("user_uuid")
    val useruuid: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("is_checked")
    val isChecked: Boolean,
    @SerializedName("category")
    val category: String,
    @SerializedName("priority")
    val priority: Int,
    @SerializedName("week_of_year")
    val weekOfYear: Int,
    @SerializedName("day_of_week")
    val dayOfWeek: Int,
    @SerializedName("assigned_at")
    val assignedAt: String
)
