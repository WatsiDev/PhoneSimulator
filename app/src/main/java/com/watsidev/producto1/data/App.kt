package com.watsidev.producto1.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class App(
    @StringRes val name: Int,
    @DrawableRes val image: Int,
    val route: Any
)
