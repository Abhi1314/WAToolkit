package com.example.pistalix.watoolkit.activity


import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.pistalix.watoolkit.BuildConfig
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.adapter.AnimatedAdapter
import com.example.pistalix.watoolkit.databinding.ActivityAnimatedEmojiBinding
import com.example.pistalix.watoolkit.model.MySingleton
import com.example.pistalix.watoolkit.utils.CommonAds
import com.example.pistalix.watoolkit.utils.gone


class AnimatedEmojiActivity : AppCompatActivity() {
    private val animatedEmojiBinding: ActivityAnimatedEmojiBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_animated_emoji)
    }

    lateinit var sharedads: SharedPreferences
    lateinit var alertDialog: Dialog
    val queue by lazy {

        MySingleton.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            sharedads = getSharedPreferences("ads", MODE_PRIVATE)
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadBannerAds(this, animatedEmojiBinding.adsContainer)
            } else {
                gone(animatedEmojiBinding.adsContainer)
            }
            alertDialog = Dialog(this, R.style.DialogCustomTheme)
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alertDialog.setContentView(R.layout.downloading_sticker_dialog)
            alertDialog.setCancelable(false)
            val tvName = alertDialog.findViewById<TextView>(R.id.loadingSticker)
            tvName!!.text = getString(R.string.loading_emoji)
            alertDialog.show()
            animatedEmojiBinding.toolBarLayout.name="Animated Emojis"
            animatedEmojiBinding.toolBarLayout.backImage.setOnClickListener {
                finish()
            }
            callCategoryList()




        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun callCategoryList() {
        val url = BuildConfig.API + "1CqTGPOwnjXU3YwviD8uwaLxsoh8j5jsCMXLvZSznOpk"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("Response42:", response.toString())
                val sheet1 = response.getJSONArray("Sheet1")
                animatedEmojiBinding.rvEmoji.adapter =
                    AnimatedAdapter(this,sheet1)
                alertDialog.dismiss()
            },
            { error ->
                Log.d("error:", error.toString())
            }
        )
        queue.addToRequestQueue(jsonObjectRequest)
    }


}