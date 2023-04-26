package com.example.pistalix.watoolkit.activity

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.databinding.ActivityShakeDetectionBinding
import com.example.pistalix.watoolkit.utils.CommonAds
import com.example.pistalix.watoolkit.utils.gone

class ShakeDetectionActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var isswitchmode = false
    private var lastUpdate: Long = 0
    private val shakeDetectionBinding: ActivityShakeDetectionBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_shake_detection)
    }
    lateinit var sharedads: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            sharedads = getSharedPreferences("ads", MODE_PRIVATE)
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadBannerAds(this, shakeDetectionBinding.adsContainer)
            } else {
                gone(shakeDetectionBinding.adsContainer)
            }
            shakeDetectionBinding.toolBarLayout.name="Shake to Open Whatsapp"
            shakeDetectionBinding.toolBarLayout.backImage.setOnClickListener { finish() }
            sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
            lastUpdate = System.currentTimeMillis()
            sensorManager.registerListener(
                this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL
            )
            shakeDetectionBinding.switchmode.setOnCheckedChangeListener { _: CompoundButton?, state: Boolean ->
                isswitchmode = state
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun getAccelerometer(event: SensorEvent) {
        val values = event.values
        // Movement
        val x = values[0]
        val y = values[1]
        val z = values[2]
        val accelationSquareRoot = ((x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH))
        val actualTime = System.currentTimeMillis()
        if (accelationSquareRoot >= 12) {
            if (actualTime - lastUpdate < 200) {
                return
            }
            lastUpdate = actualTime
            if (isswitchmode) {
                sendWhatsapp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }


    private fun sendWhatsapp() {
        val isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp")
        if (isWhatsappInstalled) {
            val intent = packageManager.getLaunchIntentForPackage("com.whatsapp")
            if (intent != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "System app is not open for some reason.", Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            Toast.makeText(this, "Whatsapp is not installed.", Toast.LENGTH_LONG).show()
        }
    }


    private fun whatsappInstalledOrNot(uri: String): Boolean {
        val pm = packageManager
        var app_installed = false
        app_installed = try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return app_installed
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event)
        }
    }

    override fun onAccuracyChanged(p0: Sensor, p1: Int) {

    }

}