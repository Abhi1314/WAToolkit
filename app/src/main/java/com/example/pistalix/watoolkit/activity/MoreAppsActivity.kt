package com.example.pistalix.watoolkit.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pistalix.watoolkit.BuildConfig
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.adapter.AppAdapter
import com.example.pistalix.watoolkit.databinding.ActivityMoreAppsBinding
import com.example.pistalix.watoolkit.utils.CommonAds
import com.example.pistalix.watoolkit.utils.gone
import com.example.pistalix.watoolkit.utils.visible
import org.json.JSONArray
import org.json.JSONObject

class MoreAppsActivity : AppCompatActivity() {
    private val moreAppBinding: ActivityMoreAppsBinding by lazy {
        DataBindingUtil.setContentView(
            this, R.layout.activity_more_apps
        )
    }

    private lateinit var sharedads: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            moreAppBinding.toolbarLayout.name= getString(R.string.more_app)
            moreAppBinding.toolbarLayout.backImage.setOnClickListener {
                finish()
            }
            sharedads = getSharedPreferences("ads", Context.MODE_PRIVATE)
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadBannerAds(this, moreAppBinding.adsContainer)
            } else {
                moreAppBinding.adsContainer.visibility = View.GONE
            }
            moreAppBinding.rvMoreApps.setHasFixedSize(true)
            setAppList()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun setAppList() {
        visible(moreAppBinding.animationViewLayout)
        val url = BuildConfig.MAIN_API + "rf=2"
        val request = Volley.newRequestQueue(this)
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.GET, url, null, { response: JSONObject ->
                try {
                    val responseArray = response.getJSONArray("records")
                    val setter = JSONArray()
                    var i = 0
                    while (i < responseArray.length()) {
                        if (responseArray.getJSONObject(i)["Package Name"] !== packageName) {
                            setter.put(responseArray[i])
                        }
                        i++
                    }
                    moreAppBinding.rvMoreApps.adapter = AppAdapter(setter)
                    gone(moreAppBinding.animationViewLayout)
                } catch (e: Exception) {
                    e.printStackTrace()
                    gone(moreAppBinding.animationViewLayout)
                }
            }) { error: VolleyError? ->
                gone(moreAppBinding.animationViewLayout)
                Log.d("err", "msg:$error")
            }
        request.add(jsonObjectRequest)
    }
}