package com.watsidev.producto1.ui.screens.apps.todo

import androidx.lifecycle.ViewModel
import com.watsidev.producto1.data.Datasource
import com.watsidev.producto1.data.ToDoTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ToDoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ToDoUiState())
    val uiState: StateFlow<ToDoUiState> = _uiState.asStateFlow()

    fun onNewTaskTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(newTaskTitle = title)
    }

    fun onNewTaskAdd() {
        val newTaskTitle = _uiState.value.newTaskTitle
        if (newTaskTitle.isNotBlank()) {
            val newTask = ToDoTask(
                id = _uiState.value.tasks.size + 1, // Generación simple de ID
                title = newTaskTitle,
                isCompleted = false
            )
            _uiState.value = _uiState.value.copy(
                tasks = _uiState.value.tasks + newTask,
                newTaskTitle = ""
            )
        }
    }

    fun onCheckedChange(taskId: Int) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.map { task ->
                if (task.id == taskId) {
                    task.copy(isCompleted = !task.isCompleted)
                } else {
                    task
                }
            }
        )
    }

    fun onDeleteTask(taskId: Int) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.filter { it.id != taskId }
        )
    }
}