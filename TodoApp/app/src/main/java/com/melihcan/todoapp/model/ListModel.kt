package com.melihcan.todoapp.model

import java.util.UUID

data class ListModel(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
)
