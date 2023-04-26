package com.example.pistalix.watoolkit.activity

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.adapter.PageAdapter
import com.example.pistalix.watoolkit.databinding.ActivityStatusPreviewBinding
import com.example.pistalix.watoolkit.utils.*
import java.io.File

class StatusPreviewActivity : AppCompatActivity() {
    private val statusPreviewBinding: ActivityStatusPreviewBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_status_preview)
    }
    private var currentPage: Int = 0
    lateinit var previewList: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        previewList = intent.getSerializableExtra("key") as ArrayList<String>
        currentPage = intent.getIntExtra("pos", 0)
        val isDownload = intent.getBooleanExtra("isDownload", true)
        if (isDownload) {
            gone(statusPreviewBinding.downloadView)
        } else {
            visible(statusPreviewBinding.downloadView)
        }
       statusPreviewBinding.backImage.setOnClickListener {
            finish()
        }
        statusPreviewBinding.viewPager.adapter =
            PageAdapter(previewList, statusPreviewBinding.root.context)
        statusPreviewBinding.viewPager.setCurrentItem(currentPage, false)
        statusPreviewBinding.downloadView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                copyFileInSavedDir(
                    statusPreviewBinding.root.context,
                    previewList[statusPreviewBinding.viewPager.currentItem]
                )
            } else {
                copyFile(
                    File(previewList[statusPreviewBinding.viewPager.currentItem]),
                    statusPreviewBinding.root.context
                )
            }
        }
        statusPreviewBinding.shareView.setOnClickListener {
            ShareFile(
                previewList[statusPreviewBinding.viewPager.currentItem],
                statusPreviewBinding.root.context
            )
        }
        statusPreviewBinding.whatsappShareView.setOnClickListener {
            openWhatsApp()
        }


    }

    private fun openWhatsApp() {
        val isWhatsappInstalled: Boolean = whatsappInstalledOrNot("com.whatsapp")
        if (isWhatsappInstalled) {
            ShareWhatsapp(
                previewList[statusPreviewBinding.viewPager.currentItem],
                statusPreviewBinding.root.context
            )
        } else {
            longShowToast(statusPreviewBinding.root.context.getString(R.string.Appnotinstalled))
        }
    }

    private fun whatsappInstalledOrNot(uri: String): Boolean {
        val pm = statusPreviewBinding.root.context.packageManager
        var app_installed = false
        app_installed = try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return app_installed
    }

}