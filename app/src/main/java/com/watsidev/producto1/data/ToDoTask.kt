package com.watsidev.producto1.data

data class ToDoTask(
    val id: Int,
    val title: String,
//    val description: String,
    val isCompleted: Boolean = false
)
