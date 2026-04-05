package com.example.blankspace.screens

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sqrt

class ShakeDetector @Inject constructor(
    private val sensorManager: SensorManager
) : SensorEventListener {

    private val _shakeEvents = MutableSharedFlow<Unit>(replay = 0)
    val shakeEvents = _shakeEvents.asSharedFlow()

    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // Parametri iz tvog članka
    private val SHAKE_THRESHOLD_GRAVITY = 2.7f
    private val SHAKE_SLOP_TIME = 500
    private var lastShakeTimestamp: Long = 0

    fun start() {
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val x = event?.values?.get(0) ?: 0f
        val y = event?.values?.get(1) ?: 0f
        val z = event?.values?.get(2) ?: 0f

        val gForce = sqrt(x*x + y*y + z*z) / SensorManager.GRAVITY_EARTH

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            val now = System.currentTimeMillis()
            if (lastShakeTimestamp + SHAKE_SLOP_TIME > now) return

            lastShakeTimestamp = now
            // Emitujemo događaj kroz Flow
            CoroutineScope(Dispatchers.Default).launch {
                _shakeEvents.emit(Unit)
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
}