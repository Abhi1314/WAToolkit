package com.example.pistalix.watoolkit.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.adapter.ToolkitAdapter
import com.example.pistalix.watoolkit.databinding.ActivityMainBinding
import com.example.pistalix.watoolkit.model.ToolkitModel
import com.example.pistalix.watoolkit.utils.CommonAds
import com.example.pistalix.watoolkit.utils.gone
import com.example.pistalix.watoolkit.utils.rateApp
import com.example.pistalix.watoolkit.utils.shareText
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    companion object {
        var click: Boolean = true
    }

    lateinit var sharedads: SharedPreferences
    private val mainBinding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    private val toolkitArrayList: ArrayList<ToolkitModel> = arrayListOf(

        ToolkitModel(1, R.string.directchat, R.drawable.ic_direct_chat),
        ToolkitModel(2, R.string.status_saver, R.drawable.ic_status_download),
        ToolkitModel(4, R.string.stylish_font, R.drawable.ic_stylefont),
        ToolkitModel(6, R.string.text_decorative, R.drawable.ic_textdecor),
        ToolkitModel(5, R.string.whatsapp_sticker, R.drawable.ic_sticker),
        ToolkitModel(8, R.string.emoji_collection, R.drawable.ic_animatedemoji),
        ToolkitModel(7, R.string.text_emoji, R.drawable.ic_text_to_emoji),
        ToolkitModel(3, R.string.whatsapp_shake, R.drawable.ic_shake_to_open),
        ToolkitModel(9, R.string.text_repeater, R.drawable.ic_textrepeat),
        ToolkitModel(0, R.string.deleted_chat, R.drawable.ic_deleted_chat),
    )
        lateinit var exitDialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            exitDialog = Dialog(this, R.style.DialogCustomTheme)
            exitDialog.setContentView(R.layout.exit_dialog)
            exitDialog.window!!.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            sharedads = getSharedPreferences("ads", Context.MODE_PRIVATE)
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadInterstitialAds(this)
            }
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadBannerAds(this, mainBinding.adsContainer)
            } else {
                gone(mainBinding.adsContainer)
            }
            mainBinding.toolsRv.adapter = ToolkitAdapter(toolkitArrayList, this)
            mainBinding.menuImg.setOnClickListener {
                mainBinding.drawerLayout.openDrawer(GravityCompat.START)
            }
            mainBinding.sidenav.llRateapp.setOnClickListener {
                mainBinding.drawerLayout.closeDrawer(GravityCompat.START)
                rateApp()

            }
            mainBinding.sidenav.llShareapp.setOnClickListener {
                mainBinding.drawerLayout.closeDrawer(GravityCompat.START)
                shareText()
            }
            mainBinding.sidenav.llMoreapp.setOnClickListener {
                mainBinding.drawerLayout.closeDrawer(GravityCompat.START)
                val intent = Intent(this, MoreAppsActivity::class.java)
                startActivity(intent)
            }
            mainBinding.sidenav.llStatus.setOnClickListener {
                mainBinding.drawerLayout.closeDrawer(GravityCompat.START)
                val intent = Intent(this, StatusSaverActivity::class.java)
                intent.putExtra("savedfragment", true)
                startActivity(intent)
            }
            setExitDialog()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setExitDialog() {

        val btnNo: TextView = exitDialog.findViewById(R.id.btnNo)
        val btnExit: TextView = exitDialog.findViewById(R.id.btnExit)
        val adsContainer: FrameLayout = exitDialog.findViewById(R.id.ads_container)
        btnNo.setOnClickListener { exitDialog.dismiss() }
        btnExit.setOnClickListener {
            exitDialog.dismiss()
            moveTaskToBack(true)
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(1)
        }
        if (sharedads.getBoolean("ads", true)) {
            CommonAds.loadNativeAd(this, adsContainer)
        } else {
            gone(adsContainer)
        }
    }


    override fun onBackPressed() {
        if (mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (!exitDialog.isShowing){
                exitDialog.show()
            }
        }
    }
}