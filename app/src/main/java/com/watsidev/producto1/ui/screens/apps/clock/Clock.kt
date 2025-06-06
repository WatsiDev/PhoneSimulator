package com.watsidev.producto1.ui.screens.apps.clock

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watsidev.producto1.R
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClockScreen() {
    val time = remember { mutableStateOf(LocalTime.now()) }
    val hour = time.value.hour

    val isDay = hour in 6..17

    val sunPosition = remember { Animatable(0f) }
    val moonPosition = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            time.value = LocalTime.now()
            val fractionOfDay = (time.value.toSecondOfDay() / 86400f)
            if (isDay) {
                sunPosition.animateTo(fractionOfDay, animationSpec = tween(1000))
            } else {
                moonPosition.animateTo(fractionOfDay, animationSpec = tween(1000))
            }
            delay(60000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isDay)
                        listOf(Color(0xFF87CEEB), Color(0xFFE0F7FA))
                    else
                        listOf(Color(0xFF0D47A1), Color(0xFF311B92))
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Draw Sun or Moon
            val cx = width * if (isDay) sunPosition.value else moonPosition.value
            val cy = height * 0.3f

            drawCircle(
                color = if (isDay) Color.Yellow else Color(0xFFE0E0E0),
                radius = 60f,
                center = Offset(cx, cy)
            )

            if (isDay) {
                // Nubes
                drawCloud(cx = width * 0.2f, cy = height * 0.2f)
                drawCloud(cx = width * 0.7f, cy = height * 0.4f)
            } else {
                // Estrellas
                drawStar(cx = width * 0.3f, cy = height * 0.2f)
                drawStar(cx = width * 0.6f, cy = height * 0.15f)
                drawStar(cx = width * 0.8f, cy = height * 0.35f)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val currentTime = remember { mutableStateOf(LocalTime.now()) }
            LaunchedEffect(Unit) {
                while (true) {
                    currentTime.value = LocalTime.now()
                    delay(1000)
                }
            }

            Text(
                text = currentTime.value.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                style = MaterialTheme.typography.displayLarge,
                color = Color.White
            )
        }
    }
}

fun DrawScope.drawCloud(cx: Float, cy: Float) {
    drawCircle(Color.White, 40f, Offset(cx, cy))
    drawCircle(Color.White, 30f, Offset(cx + 40f, cy - 20f))
    drawCircle(Color.White, 35f, Offset(cx + 80f, cy))
}

fun DrawScope.drawStar(cx: Float, cy: Float) {
    drawCircle(Color.White, radius = 5f, center = Offset(cx, cy))
}
