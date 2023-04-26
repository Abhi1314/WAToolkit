package com.example.pistalix.watoolkit.activity

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.example.pistalix.watoolkit.BuildConfig
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.databinding.ActivityAnimatedDetailsBinding
import com.example.pistalix.watoolkit.utils.CommonAds
import com.example.pistalix.watoolkit.utils.gone
import com.example.pistalix.watoolkit.utils.longShowToast
import com.example.pistalix.watoolkit.utils.shortShowToast
import java.io.File


class AnimatedDetailsActivity : AppCompatActivity() {
    private val animatedDetailsBinding: ActivityAnimatedDetailsBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_animated_details)

    }
    lateinit var sharedads: SharedPreferences
    lateinit var url: String
    lateinit var alertDialog: Dialog
    var type = "download"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            PRDownloader.initialize(this)
            sharedads = getSharedPreferences("ads", MODE_PRIVATE)
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadNativeAd(this, animatedDetailsBinding.adsContainer)
            } else {
                gone(animatedDetailsBinding.adsContainer)
            }
            animatedDetailsBinding.toolBarLayout.name = intent.getStringExtra("category")
            animatedDetailsBinding.toolBarLayout.backImage.setOnClickListener {
                finish()
            }
            alertDialog = Dialog(this, R.style.DialogCustomTheme)
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alertDialog.setContentView(R.layout.downloading_sticker_dialog)
            alertDialog.setCancelable(false)
            val tvName = alertDialog.findViewById<TextView>(R.id.loadingSticker)
            url = intent.getStringExtra("Sticker_url")!!
            Glide.with(this@AnimatedDetailsActivity).load(url)
                .placeholder(R.drawable.ic_placeholder).into(animatedDetailsBinding.emojiView)
            animatedDetailsBinding.shareView.setOnClickListener {
                tvName.text = getString(R.string.loading1)
                alertDialog.show()
                type = "share"
                permission()
            }
            animatedDetailsBinding.downloadView.setOnClickListener {
                tvName.text = getString(R.string.download_emoji)
                alertDialog.show()
                type = "download"
                permission()

            }
            animatedDetailsBinding.whatsappShareView.setOnClickListener {
                tvName.text = getString(R.string.loading1)
                alertDialog.show()
                type = "whatsappshare"
                permission()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun permission() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) + ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            downloadGif(url)
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 101
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadGif(url)
            }
        }
    }

    private fun downloadGif(url: String) {
        val file = if (type == "share" || type == "whatsappshare") {
            File("$filesDir/gif_images")
        } else {
            File(
                Environment.getExternalStorageDirectory()
                    .toString() + "/Download/" + resources.getString(R.string.app_name) + "/gif_images"
            )
        }
        if (!file.exists()) {
            file.mkdirs()
        }


        val fileName = url.substring(url.lastIndexOf('/') + 1)
        val downloadfile = File("${file.path}/$fileName")
        if (!downloadfile.exists()) {
            PRDownloader.download(url, file.absolutePath, fileName).build()
                .setOnStartOrResumeListener { }.setOnPauseListener { }
                .setOnCancelListener {}.setOnProgressListener {}
                .start(object : OnDownloadListener {
                    override fun onDownloadComplete() {
                        alertDialog.dismiss()
                        if (type == "download") {
                            shortShowToast("Download completed")
                        }
                        if (downloadfile.exists()) {
                            if (type == "share") {
                                val uri: Uri = FileProvider.getUriForFile(
                                    this@AnimatedDetailsActivity,
                                    BuildConfig.APPLICATION_ID + ".provider",
                                    downloadfile
                                )
                                val shareIntent = Intent(Intent.ACTION_SEND)
                                shareIntent.type = "image/gif"
                                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                                startActivity(shareIntent)
                            } else if (type == "whatsappshare") {
                                val uri: Uri = FileProvider.getUriForFile(
                                    this@AnimatedDetailsActivity,
                                    BuildConfig.APPLICATION_ID + ".provider",
                                    downloadfile
                                )
                                val isWhatsappInstalled: Boolean =
                                    whatsappInstalledOrNot("com.whatsapp")
                                if (isWhatsappInstalled) {
                                    val shareIntent = Intent(Intent.ACTION_SEND)
                                    shareIntent.type = "image/gif"
                                    shareIntent.`package` = "com.whatsapp"
                                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                                    startActivity(shareIntent)
                                } else {
                                    longShowToast(getString(R.string.Appnotinstalled))
                                }


                            }


                        }

                    }

                    override fun onError(error: com.downloader.Error?) {


                    }

                })
        } else {
            if (type == "download") {
                longShowToast("file is already exist")
            }
            alertDialog.dismiss()
            if (type == "share") {
                val uri: Uri = FileProvider.getUriForFile(
                    this@AnimatedDetailsActivity,
                    BuildConfig.APPLICATION_ID + ".provider",
                    downloadfile
                );
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "image/*"
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                startActivity(Intent.createChooser(shareIntent, "Share Emoji"))
            } else if (type == "whatsappshare") {
                val uri: Uri = FileProvider.getUriForFile(
                    this@AnimatedDetailsActivity,
                    BuildConfig.APPLICATION_ID + ".provider",
                    downloadfile
                )
                val isWhatsappInstalled: Boolean = whatsappInstalledOrNot("com.whatsapp")
                if (isWhatsappInstalled) {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "image/gif"
                    shareIntent.`package` = "com.whatsapp"
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                    startActivity(shareIntent)
                } else {
                    longShowToast(getString(R.string.Appnotinstalled))
                }
            }
        }
    }

    override fun onDestroy() {
        val file = File("$filesDir/gif_images")
        val fileName = url.substring(url.lastIndexOf('/') + 1)
        val downloadfile = File("${file.path}/$fileName")
        if (downloadfile.exists()) {
            downloadfile.delete()
        }
        super.onDestroy()
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
}