@file:OptIn(ExperimentalPermissionsApi::class)

package com.watsidev.producto1.ui.screens.apps.scanner

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@Composable
fun ScannerScreen(viewModel: ScannerViewModel = viewModel()) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    if (permissionState.status.isGranted) {
        val cameraController = remember {
            LifecycleCameraController(context).apply {
                setImageAnalysisAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    BarcodeAnalyzer { scanned ->
                        viewModel.onScanned(scanned)
                    }
                )
            }
        }
        val lifecycleOwner = LocalLifecycleOwner.current
        cameraController.bindToLifecycle(lifecycleOwner)

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(factory = {
                PreviewView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    controller = cameraController
                }
            })

            // Mostrar resultado si hay texto escaneado
            viewModel.scannedText?.let { text ->
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .background(Color.White)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Código escaneado:",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = text,
                        color = Color.Blue,
                        modifier = Modifier
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(text))
                                context.startActivity(intent)
                            }
                            .padding(4.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { viewModel.resetScan() }) {
                        Text("Escanear otro")
                    }
                }
            }
        }
    } else {
        Text("Permiso de cámara denegado.")
    }
}
