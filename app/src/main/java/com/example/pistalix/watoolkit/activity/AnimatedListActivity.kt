package com.example.pistalix.watoolkit.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.downloader.PRDownloader
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.adapter.AnimatedEmojiAdapter
import com.example.pistalix.watoolkit.databinding.ActivityAnimatedListBinding
import com.example.pistalix.watoolkit.utils.CommonAds
import com.example.pistalix.watoolkit.utils.gone
import org.json.JSONObject

class AnimatedListActivity : AppCompatActivity() {



    private  val animatedListBinding:ActivityAnimatedListBinding by lazy {
        DataBindingUtil.setContentView(this,R.layout.activity_animated_list)
    }
    lateinit var sharedads: SharedPreferences
    lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            PRDownloader.initialize(this)
            sharedads = getSharedPreferences("ads", MODE_PRIVATE)
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadBannerAds(this, animatedListBinding.adsContainer)
            } else {
                gone(animatedListBinding.adsContainer)
            }
            animatedListBinding.toolBarLayout.name =intent.getStringExtra("category")
            animatedListBinding.toolBarLayout.backImage.setOnClickListener {
                finish()
            }
            val emojiResposnse= intent.getStringExtra("Sheetid")!!
            val item1= JSONObject(emojiResposnse)
            val itemarray=item1.getJSONArray("Sheet1")
            animatedListBinding.recyclerView.adapter= AnimatedEmojiAdapter(this,itemarray)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


}