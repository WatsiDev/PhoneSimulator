package com.watsidev.producto1.ui.screens

import com.watsidev.producto1.data.App

data class HomeUiState(
    val listApps: List<App> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
