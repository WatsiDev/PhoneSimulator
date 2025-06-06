package com.watsidev.producto1.ui.screens.apps.compass

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CompassViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {

    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var accelerometerValues = FloatArray(3)
    private var magneticValues = FloatArray(3)

    private val _uiState = MutableStateFlow(CompassUiState())
    val uiState: StateFlow<CompassUiState> = _uiState

    fun start() {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> accelerometerValues = event.values.clone()
            Sensor.TYPE_MAGNETIC_FIELD -> magneticValues = event.values.clone()
        }

        val rotationMatrix = FloatArray(9)
        val orientation = FloatArray(3)

        if (SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerValues, magneticValues)) {
            SensorManager.getOrientation(rotationMatrix, orientation)
            val azimuthRadians = orientation[0]
            val azimuthDegrees = ((Math.toDegrees(azimuthRadians.toDouble()) + 360) % 360).toFloat()

            _uiState.value = CompassUiState(azimuth = azimuthDegrees)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
