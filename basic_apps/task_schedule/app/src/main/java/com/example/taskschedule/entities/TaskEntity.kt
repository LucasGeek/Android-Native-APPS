package com.example.taskschedule.entities

data class TaskEntity(
    val id: Int,
    var userId: Int,
    val priorityId: Int,
    val description: String,
    var complete: Boolean,
    val dueDate: String
)