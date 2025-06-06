package com.watsidev.producto1.ui.screens.apps.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.watsidev.producto1.data.Datasource
import com.watsidev.producto1.data.ToDoTask

@Composable
fun TodoTaskScreen(
    viewModel: ToDoViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = { ToDoTaskTopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .height(55.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedTextField(
                    value = uiState.value.newTaskTitle,
                    onValueChange = { viewModel.onNewTaskTitleChange(it) },
                    shape = RoundedCornerShape(8.dp),
                    placeholder = { Text("New Task") },
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(1f)
                )
                Button(
                    onClick = { viewModel.onNewTaskAdd() },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .height(55.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Task",
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            TaskTabs(
                listTask = uiState.value.tasks,
                onCheckedChange = { viewModel.onCheckedChange(it) },
                onDeleteTask = { viewModel.onDeleteTask(it) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoTaskTopBar() {
    CenterAlignedTopAppBar(
        title = {Text("To-Do Task")},
    )
}

@Composable
fun TaskTabs(
    listTask: List<ToDoTask>,
    onCheckedChange: (Int) -> Unit,
    onDeleteTask: (Int) -> Unit
) {
    val tabs = listOf(
        TabData("All", Icons.Filled.ClearAll),
        TabData("Active", Icons.AutoMirrored.Filled.Assignment),
        TabData("Completed", Icons.Filled.TaskAlt)
    )
    val selectedTab = remember { mutableStateOf(0) }

    Column {
        TabRow(selectedTabIndex = selectedTab.value) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTab.value == index,
                    onClick = { selectedTab.value = index },
                    text = { Text(text = tab.title) },
                    icon = { Icon(
                        imageVector = tab.icon,
                        contentDescription = null // Provide a content description if needed
                    )}
                )
            }
        }

        // Contenido para cada tab
        when (selectedTab.value) {
            0 -> AllTaskScreen(listTask, onCheckedChange = { onCheckedChange(it) }, onDeleteTask = { onDeleteTask(it) })
            1 -> ActiveTaskScreen(listTask, onCheckedChange = { onCheckedChange(it) }, onDeleteTask = { onDeleteTask(it) })
            2 -> CompletedTaskScreen(listTask, onCheckedChange = { onCheckedChange(it) }, onDeleteTask = { onDeleteTask(it) })
        }
    }
}

//Creamos una data class para el texto y el titulo del Tab
data class TabData(val title: String, val icon: ImageVector)

@Composable
fun AllTaskScreen(
    listTask: List<ToDoTask>,
    onCheckedChange: (Int) -> Unit,
    onDeleteTask: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listTask.size) { task ->
            TaskItem(task = listTask[task],
                onCheckedChange = { onCheckedChange(it) },
                onDeleteTask = { onDeleteTask(it) },
                modifier = Modifier.animateItem()
            )
        }
    }
}

@Composable
fun ActiveTaskScreen(
    listTask: List<ToDoTask>,
    onCheckedChange: (Int) -> Unit,
    onDeleteTask: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listTask.size) { task ->
            if (!listTask[task].isCompleted) TaskItem(task = listTask[task],
                onCheckedChange = { onCheckedChange(it) },
                onDeleteTask = { onDeleteTask(it) },
                modifier = Modifier.animateItem()
            )
        }
    }
}

@Composable
fun CompletedTaskScreen(
    listTask: List<ToDoTask>,
    onCheckedChange: (Int) -> Unit,
    onDeleteTask: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listTask.size) { task ->
            if (listTask[task].isCompleted) TaskItem(task = listTask[task],
                onCheckedChange = { onCheckedChange(it) },
                onDeleteTask = { onDeleteTask(it) },
                modifier = Modifier.animateItem()
            )
        }
    }
}

@Composable
fun TaskItem(
    task: ToDoTask,
    onCheckedChange: (Int) -> Unit,
    onDeleteTask: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth()
            .height(65.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onCheckedChange(task.id) }
        ) {
            Icon(
                imageVector = if (task.isCompleted) Icons.Filled.CheckBox else Icons.Default.CheckBoxOutlineBlank,
                contentDescription = if (task.isCompleted) "Task Completed" else "Task Incomplete",
                tint = if (task.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
        Text(
            task.title,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        IconButton(
            onClick = { onDeleteTask(task.id) },
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete Task",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}