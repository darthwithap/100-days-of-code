package me.darthwithap.hardwaresensors

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.UnsupportedOperationException
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var sensorsManager: SensorManager
    private lateinit var proximitySensor: Sensor
    private lateinit var accelerometerSensor: Sensor
    private lateinit var proximitySensorEventListener: SensorEventListener
    private lateinit var accelerometerSensorEventListener: SensorEventListener
    private val colors = arrayOf(Color.BLUE, Color.GRAY, Color.DKGRAY, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.YELLOW, Color.LTGRAY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorsManager = getSystemService()!!
        proximitySensor = sensorsManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        accelerometerSensor = sensorsManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        proximitySensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

            override fun onSensorChanged(event: SensorEvent?) {
                if (event!!.values[0] > 0) {
                    flTop.setBackgroundColor(colors[(colors.indices).random()])
                }
            }
        }

        accelerometerSensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

            override fun onSensorChanged(event: SensorEvent?) {
                flBottom.setBackgroundColor(
                        Color.rgb(
                                accelerationToColor(event!!.values[0]),
                                accelerationToColor(event!!.values[1]),
                                accelerationToColor(event!!.values[2]
                                )
                        )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorsManager.registerListener(proximitySensorEventListener, proximitySensor, 1000 * 1000)
        sensorsManager.registerListener(accelerometerSensorEventListener, accelerometerSensor, 1000 * 1000)
    }

    override fun onPause() {
        sensorsManager.unregisterListener(accelerometerSensorEventListener)
        sensorsManager.unregisterListener(proximitySensorEventListener)
        super.onPause()
    }

    private fun accelerationToColor(acceleration: Float) = ((acceleration + 12) / 24 * 255).roundToInt()
}