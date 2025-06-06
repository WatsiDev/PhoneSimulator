package com.watsidev.producto1.ui.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TimeWidget(modifier: Modifier = Modifier) {
    val time by remember { mutableStateOf(LocalTime.now()) }
    val date by remember { mutableStateOf(LocalDate.now()) }

    // Actualiza cada segundo
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
        }
    }

    // Determina el color del fondo según la hora 🌞🌙
    val backgroundBrush = when (time.hour) {
        in 6..11 -> Brush.verticalGradient(listOf(Color(0xFFB3E5FC), Color(0xFF81D4FA))) // Mañana (Azul suave)
        in 12..16 -> Brush.verticalGradient(listOf(Color(0xFF64B5F6), Color(0xFF42A5F5))) // Mediodía (Azul fuerte)
        in 17..19 -> Brush.verticalGradient(listOf(Color(0xFF002f6c), Color(0xFF4a90e2), Color(0xFFffb74d))) // Atardecer (Naranja/Azul)
        else -> Brush.verticalGradient(listOf(Color(0xFF263238), Color(0xFF000000))) // Noche (Oscuro)
    }

    // Formatear la hora con tamaño más pequeño para "a.m." / "p.m."
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    val formattedTime = time.format(formatter)

    val annotatedTime = buildAnnotatedString {
        val parts = formattedTime.split(" ")
        append(parts[0]) // Hora y minutos
        withStyle(style = SpanStyle(fontSize = 20.sp)) {
            append(" ${parts[1]}") // "a.m." / "p.m." con tamaño menor
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(backgroundBrush)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = annotatedTime,
                fontSize = 40.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
            Text(
                text = date.format(DateTimeFormatter.ofPattern("EEE, d MMM.")),
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}