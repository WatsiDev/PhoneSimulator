package com.watsidev.producto1.ui.screens.apps.scanner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ScannerViewModel : ViewModel() {
    var scannedText by mutableStateOf<String?>(null)
        private set

    fun onScanned(text: String) {
        scannedText = text
    }

    fun resetScan() {
        scannedText = null
    }
}
