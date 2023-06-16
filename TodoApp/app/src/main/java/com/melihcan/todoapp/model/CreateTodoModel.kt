package com.melihcan.todoapp.model

import com.google.gson.annotations.SerializedName

data class CreateTodoModel(
    @SerializedName("user_uuid")
    var useruuid: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("detail")
    val detail: String = "EmptyForNow",
    @SerializedName("category")
    val category: String,
    @SerializedName("priority")
    val priority: Int = 1,
    @SerializedName("week_of_year")
    val weekOfYear: Int,
    @SerializedName("day_of_week")
    val dayOfWeek: Int,
    @SerializedName("assigned_at")
    val assignedAt: String
)