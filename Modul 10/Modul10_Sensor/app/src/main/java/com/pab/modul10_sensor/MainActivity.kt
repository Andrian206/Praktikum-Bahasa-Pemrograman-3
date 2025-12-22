package com.pab.modul10_sensor

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var proximitySensor: Sensor? = null
    private lateinit var vibrator: Vibrator
    private lateinit var cameraManager: CameraManager
    private var cameraId: String? = null
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var mainLayout: LinearLayout
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupComponents()
    }

    // Metode untuk menginisialisasi semua komponen yang diperlukan
    private fun setupComponents() {
        mainLayout = findViewById(R.id.main)
        textView = findViewById(R.id.warningTextView)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        if (proximitySensor == null) {
            textView.text = "Sensor Jarak tidak ditemukan di perangkat ini."
            Toast.makeText(this, "Sensor Jarak tidak tersedia", Toast.LENGTH_LONG).show()
        }

        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)

        // Mendapatkan ID kamera
        initCameraId()
    }

    // Mendapatkan ID kamera pertama yang tersedia
    private fun initCameraId() {
        try {
            val cameraIds = cameraManager.cameraIdList
            if (cameraIds.isNotEmpty()) {
                cameraId = cameraIds[0]
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    // Mendaftarkan listener sensor saat aktivitas dilanjutkan
    override fun onResume() {
        super.onResume()
        // Hanya daftarkan listener jika sensor ada
        proximitySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    // Membatalkan pendaftaran listener dan mematikan flash saat aktivitas dijeda
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        turnOffFlash()
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        }
    }

    // Menangani perubahan pada sensor proximity
    override fun onSensorChanged(event: SensorEvent) {
        if (event.values[0] < (proximitySensor?.maximumRange ?: 0f)) {
            // Jika objek dekat, aktifkan alarm
            triggerProximityAlerts()
        } else {
            // Jika objek jauh, reset UI
            resetUI()
        }
    }

    // Mengaktifkan alarm: menyalakan flash, getaran, mengubah warna latar, dan memutar suara
    private fun triggerProximityAlerts() {
        turnOnFlash()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(500) // Untuk versi Android di bawah Oreo
        }
        mainLayout.setBackgroundColor(Color.RED)
        textView.text = "Jarak Terlalu Dekat!"
        playAlarmSound()
    }

    private fun resetUI() {
        turnOffFlash()
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            mediaPlayer?.seekTo(0)
        }
        mainLayout.setBackgroundColor(Color.WHITE)
        textView.text = "Proximity Sensor Active"
    }

    // Tidak digunakan dalam implementasi ini
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    // Menyalakan flash
    private fun turnOnFlash() = setFlashlight(true)

    // Mematikan flash
    private fun turnOffFlash() = setFlashlight(false)

    // Mengatur status flash (nyala/mati)
    private fun setFlashlight(status: Boolean) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraId?.let { id ->
                    val characteristics = cameraManager.getCameraCharacteristics(id)
                    val hasFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) ?: false
                    if (hasFlash) {
                        cameraManager.setTorchMode(id, status)
                    }
                }
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    // Memutar suara alarm jika belum diputar
    private fun playAlarmSound() {
        if (mediaPlayer?.isPlaying == false) {
            mediaPlayer?.start()
        }
    }

    // Melepaskan sumber daya media player saat aktivitas dihancurkan
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}