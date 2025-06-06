package com.watsidev.producto1.ui.screens.apps.todo

import com.watsidev.producto1.data.Datasource
import com.watsidev.producto1.data.ToDoTask

data class ToDoUiState(
    val tasks: List<ToDoTask> = Datasource.ToDoList,
//    val isLoading: Boolean = false,
//    val error: String? = null,
    val newTaskTitle: String = "",
//    val newTaskDescription: String = ""
)
