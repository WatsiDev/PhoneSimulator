package com.watsidev.producto1.data

import com.watsidev.producto1.R
import com.watsidev.producto1.ui.screens.Calculator
import com.watsidev.producto1.ui.screens.Clock
import com.watsidev.producto1.ui.screens.Compass
import com.watsidev.producto1.ui.screens.Phone
import com.watsidev.producto1.ui.screens.Scanner
import com.watsidev.producto1.ui.screens.ToDo

object Datasource {
    var listApps = listOf(
        App(
            R.string.clock_app,
            R.drawable.clock,
            Clock
        ),
        App(
            R.string.phone_app,
            R.drawable.phone,
            Phone
        ),
        App(
            R.string.scanner_app,
            R.drawable.camera,
            Scanner
        ),
        App(
            R.string.compass_app,
            R.drawable.compass,
            Compass
        ),
        App(
            R.string.calculator_app,
            R.drawable.calculator,
            Calculator
        ),
        App(
            R.string.to_do_task,
            R.drawable.compass,
            ToDo
        )
    )
    var ToDoList = mutableListOf<ToDoTask>(
        ToDoTask(
            id = 1,
            title = "Comprar pan",
            isCompleted = false
        ),
        ToDoTask(
            id = 2,
            title = "Lavar ropa",
            isCompleted = true
        ),
    )
}