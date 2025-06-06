package com.watsidev.producto1.ui.screens

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.watsidev.producto1.R
import kotlinx.coroutines.delay
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
) {

    var time by remember { mutableStateOf(LocalTime.now()) }
    var date by remember { mutableStateOf(LocalDate.now()) }
    LaunchedEffect(Unit) {
        while (true) {
            time = LocalTime.now()
            date = LocalDate.now()
            delay(1000)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painterResource(R.drawable.loginbg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                time.format(DateTimeFormatter.ofPattern("hh:mm a")),
                fontSize = 40.sp
            )
            Text(
                date.format(DateTimeFormatter.ofPattern("EEEE, d MMMM")),
                fontSize = 20.sp
            )
            Spacer(Modifier.weight(1f))
            Biometric(modifier = Modifier.padding(50.dp), onClick = { navigateToHome() })
        }
    }
}

@Composable
fun Biometric(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val context = LocalContext.current
    val vibrator = context.getSystemService(Vibrator::class.java)
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.biometric))
    var isPlaying by remember { mutableStateOf(false) }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying
    )

    Box(
        modifier = modifier
            .size(64.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPlaying = true
                        tryAwaitRelease()
                        isPlaying = false
                        onClick()
                    }
                )
            }
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.fillMaxSize()
        )
    }

    // Activa la vibración cuando la animación termine
    if (progress == 1f) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(150) // Para versiones antiguas
        }
    }
}