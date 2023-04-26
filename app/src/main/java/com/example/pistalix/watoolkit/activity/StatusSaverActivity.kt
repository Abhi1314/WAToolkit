package com.example.pistalix.watoolkit.activity

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.databinding.ActivityStatusSaverBinding
import com.example.pistalix.watoolkit.fragment.SavedFragment
import com.example.pistalix.watoolkit.fragment.StatusFragment
import com.example.pistalix.watoolkit.utils.CommonAds
import com.example.pistalix.watoolkit.utils.gone

class StatusSaverActivity : AppCompatActivity() {
    private val statusSaverBinding: ActivityStatusSaverBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_status_saver)
    }
    private val Ask_multiple_requestcode = 101
    lateinit var sharedads: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            sharedads = getSharedPreferences("ads", MODE_PRIVATE)
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadBannerAds(this, statusSaverBinding.adsContainer)
            } else {
                gone(statusSaverBinding.adsContainer)
            }
            statusSaverBinding.toolBarLayout.name = getString(R.string.statussaver)
            statusSaverBinding.toolBarLayout.backImage.setOnClickListener { finish() }
            statusSaverBinding.permissionBtn.setOnClickListener {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), Ask_multiple_requestcode
                )
            }


            permission()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun permission() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) + ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            statusSaverBinding.permissionText.visibility = View.VISIBLE
            statusSaverBinding.permissionBtn.visibility = View.VISIBLE

        } else {
            getAdapterSet()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Ask_multiple_requestcode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getAdapterSet()
            }
        }
    }

    private fun getAdapterSet() {
        statusSaverBinding.permissionText.visibility = View.GONE
        statusSaverBinding.permissionBtn.visibility = View.GONE
        statusSaverBinding.statusView.visibility = View.VISIBLE
        statusSaverBinding.savedView.visibility = View.VISIBLE

        if (intent.getBooleanExtra("savedfragment", false)) {
            statusSaverBinding.statusView.isSelected = false
            statusSaverBinding.savedView.isSelected = true
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, SavedFragment())
                .commit()
        } else {
            statusSaverBinding.statusView.isSelected = true
            statusSaverBinding.savedView.isSelected = false
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, StatusFragment())
                .commit()
        }

        statusSaverBinding.statusView.setOnClickListener {
            statusSaverBinding.statusView.isSelected = true
            statusSaverBinding.savedView.isSelected = false
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, StatusFragment())
                .commit()

        }
        statusSaverBinding.savedView.setOnClickListener {
            statusSaverBinding.statusView.isSelected = false
            statusSaverBinding.savedView.isSelected = true
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, SavedFragment())
                .commit()

        }

    }


}