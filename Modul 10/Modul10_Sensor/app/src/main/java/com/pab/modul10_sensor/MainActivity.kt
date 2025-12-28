package com.pab.modul10_sensor

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.TextView
import android.media.MediaPlayer
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var proximitySensor: Sensor? = null
    private var lightSensor: Sensor? = null
    private lateinit var vibrator: Vibrator
    private lateinit var cameraManager: CameraManager
    private var cameraId: String? = null
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var textView: TextView
    private var isAlertActive = false
    private val handler = Handler(Looper.getMainLooper())
    private var sensorCheckCount = 0
    private var useProximitySensor = true
    private var lightThreshold = 10f
    private var lastLightValue = -1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupComponents()
    }

    private fun setupComponents() {
        mainLayout = findViewById(R.id.mainLayout)
        textView = findViewById(R.id.warningTextView)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        
        if (proximitySensor == null && lightSensor == null) {
            textView.text = "Tidak ada sensor yang tersedia!"
        }

        initCameraId()
    }

    private fun initCameraId() {
        try {
            val cameraIds = cameraManager.cameraIdList
            for (id in cameraIds) {
                val characteristics = cameraManager.getCameraCharacteristics(id)
                val hasFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
                if (hasFlash == true) {
                    cameraId = id
                    android.util.Log.d("FlashDebug", "Found camera with flash: $id")
                    break
                }
            }
            if (cameraId == null && cameraIds.isNotEmpty()) {
                cameraId = cameraIds[0]
                android.util.Log.d("FlashDebug", "Using fallback camera: ${cameraId}")
            }
        } catch (e: Exception) {
            android.util.Log.e("FlashDebug", "Error finding camera: ${e.message}")
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        sensorCheckCount = 0
        useProximitySensor = true

        sensorManager.unregisterListener(this)

        if (proximitySensor != null) {
            val registered = sensorManager.registerListener(
                this, 
                proximitySensor, 
                SensorManager.SENSOR_DELAY_FASTEST
            )
            android.util.Log.d("ProximitySensor", "Proximity sensor registered: $registered")
        }

        if (lightSensor != null) {
            val registered = sensorManager.registerListener(
                this, 
                lightSensor, 
                SensorManager.SENSOR_DELAY_FASTEST
            )
            android.util.Log.d("LightSensor", "Light sensor registered: $registered")
            android.util.Log.d("LightSensor", "Light sensor name: ${lightSensor?.name}")
            android.util.Log.d("LightSensor", "Light sensor max range: ${lightSensor?.maximumRange}")
        }

        textView.text = "Tutup bagian atas HP untuk test\n(Menggunakan sensor cahaya)\n\nKetuk layar untuk test manual"

        startSensorCheck()

        mainLayout.setOnClickListener {
            android.util.Log.d("ManualTest", "Screen tapped - testing flash and alarm")
            if (!isAlertActive) {
                isAlertActive = true
                triggerProximityAlerts()
                // Auto reset setelah 3 detik
                handler.postDelayed({
                    isAlertActive = false
                    resetUI()
                }, 3000)
            }
        }
    }
    
    private fun startSensorCheck() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                sensorCheckCount++
                android.util.Log.d("ProximitySensor", "Sensor check #$sensorCheckCount - waiting for events...")
                if (sensorCheckCount < 10) {
                    handler.postDelayed(this, 2000)
                }
            }
        }, 2000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
        sensorManager.unregisterListener(this)
        turnOffFlash()
        stopAlarmSound()
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_PROXIMITY -> {
                val distance = event.values[0]
                val maxRange = proximitySensor?.maximumRange ?: 5f

                android.util.Log.d("ProximitySensor", "Distance: $distance, MaxRange: $maxRange")

                useProximitySensor = true

                val isNear = distance < maxRange

                if (isNear && !isAlertActive) {
                    android.util.Log.d("ProximitySensor", "Object NEAR - Triggering alerts")
                    isAlertActive = true
                    triggerProximityAlerts()
                } else if (!isNear && isAlertActive) {
                    android.util.Log.d("ProximitySensor", "Object FAR - Resetting UI")
                    isAlertActive = false
                    resetUI()
                }
            }
            
            Sensor.TYPE_LIGHT -> {
                val lightValue = event.values[0]

                if (kotlin.math.abs(lightValue - lastLightValue) > 5f || lastLightValue < 0) {
                    android.util.Log.d("LightSensor", "Light value: $lightValue lux")
                    lastLightValue = lightValue
                }

                val isDark = lightValue < lightThreshold
                
                if (isDark && !isAlertActive) {
                    android.util.Log.d("LightSensor", "LOW LIGHT detected ($lightValue lux) - Triggering alerts")
                    isAlertActive = true
                    triggerProximityAlerts()
                } else if (!isDark && isAlertActive) {
                    android.util.Log.d("LightSensor", "LIGHT restored ($lightValue lux) - Resetting UI")
                    isAlertActive = false
                    resetUI()
                }
            }
        }
    }

    private fun triggerProximityAlerts() {
        turnOnFlash()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(500)
        }
        mainLayout.setBackgroundColor(Color.RED)
        textView.text = "Jarak Terlalu Dekat!"
        playAlarmSound()
    }

    private fun resetUI() {
        turnOffFlash()
        stopAlarmSound()
        mainLayout.setBackgroundColor(Color.WHITE)
        textView.text = "Proximity Sensor Active"
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    private fun turnOnFlash() = setFlashlight(true)

    private fun turnOffFlash() = setFlashlight(false)

    private fun setFlashlight(status: Boolean) {
        try {
            if (cameraId != null) {
                cameraManager.setTorchMode(cameraId!!, status)
                android.util.Log.d("FlashDebug", "Flash set to: $status with cameraId: $cameraId")
            } else {
                android.util.Log.e("FlashDebug", "Cannot set flash - cameraId is null")
            }
        } catch (e: Exception) {
            android.util.Log.e("FlashDebug", "Error setting flash: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun playAlarmSound() {
        try {
            if (mediaPlayer != null && mediaPlayer?.isPlaying == false) {
                mediaPlayer?.isLooping = true
                mediaPlayer?.start()
                android.util.Log.d("AlarmDebug", "Alarm started playing")
            }
        } catch (e: Exception) {
            android.util.Log.e("AlarmDebug", "Error playing alarm: ${e.message}")
        }
    }

    private fun stopAlarmSound() {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                mediaPlayer?.seekTo(0)
                android.util.Log.d("AlarmDebug", "Alarm stopped")
            }
        } catch (e: Exception) {
            android.util.Log.e("AlarmDebug", "Error stopping alarm: ${e.message}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}