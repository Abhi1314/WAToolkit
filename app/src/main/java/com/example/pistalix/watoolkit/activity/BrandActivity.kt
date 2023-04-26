package com.example.pistalix.watoolkit.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pistalix.watoolkit.BuildConfig
import com.example.pistalix.watoolkit.R
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import org.json.JSONObject

class BrandActivity : AppCompatActivity() {
    lateinit var sharedads:SharedPreferences
    var mInterstitialAd:InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand)

        try {
            splashScreen.setKeepOnScreenCondition {true}
            sharedads=getSharedPreferences("ads", Context.MODE_PRIVATE)

            Handler(Looper.getMainLooper()).postDelayed({
                val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                val nwInfo = connectivityManager.activeNetworkInfo
                if (nwInfo != null && nwInfo.isConnectedOrConnecting)
                {
                    try
                    {
                        val url = BuildConfig.MAIN_API + "tId=1"
                        val currenversion: Int = BuildConfig.VERSION_CODE
                        val request = Volley.newRequestQueue(this@BrandActivity)
                        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response: JSONObject ->
                            try
                            {
                                if (response.getJSONArray("records").getJSONObject(0).getInt("Ads") == 1)
                                {
                                    val editor = sharedads.edit()
                                    editor.putBoolean("ads", true)
                                    editor.commit()
                                }
                                else
                                {
                                    val editor = sharedads.edit()
                                    editor.putBoolean("ads", false)
                                    editor.commit()
                                }

                                if (response.getJSONArray("records").getJSONObject(0).getString("Package Name") != applicationContext.packageName)
                                {
                                    val editor = sharedads.edit()
                                    editor.putBoolean("Publish", false)
                                    editor.putString("NewApp", response.getJSONArray("records").getJSONObject(0).getString("Package Name"))
                                    editor.commit()
                                }
                                else
                                {
                                    val editor = sharedads.edit()
                                    editor.putBoolean("Publish", true)
                                    editor.putString("NewApp", response.getJSONArray("records").getJSONObject(0).getString("Package Name"))
                                    editor.commit()
                                }

                                if (response.getJSONArray("records").getJSONObject(0).getInt("Virsion") > currenversion)
                                {
                                    val editor = sharedads.edit()
                                    editor.putBoolean("version", false)
                                    editor.putInt("New", response.getJSONArray("records").getJSONObject(0).getInt("Virsion"))
                                    editor.commit()
                                }
                                else
                                {
                                    val editor = sharedads.edit()
                                    editor.putBoolean("version", true)
                                    editor.putInt("New", response.getJSONArray("records").getJSONObject(0).getInt("Virsion"))
                                    editor.commit()
                                }

                                if(sharedads.getBoolean("ads", true))
                                {
                                    loadInterstitial()
                                }
                                else
                                {
                                    startActivity(Intent(this@BrandActivity,MainActivity::class.java))
                                    finish()
                                }
                            }
                            catch (e: Exception)
                            {
                                e.printStackTrace()
                                startActivity(Intent(this@BrandActivity,MainActivity::class.java))
                                finish()
                            }
                        }) { error: VolleyError ->
                            Log.d("err", "msg:$error")
                            startActivity(Intent(this@BrandActivity,MainActivity::class.java))
                            finish()
                        }

                        jsonObjectRequest.setShouldCache(false)
                        request.add(jsonObjectRequest)
                    }
                    catch (e: Exception)
                    {
                        e.printStackTrace()
                        startActivity(Intent(this@BrandActivity,MainActivity::class.java))
                        finish()
                    }
                }
                else
                {
                    Toast.makeText(this@BrandActivity, getString(R.string.check_your_network_connection), Toast.LENGTH_SHORT).show()
                }
            },1000)
        }catch (e:Exception){
            e.printStackTrace()
        }


    }
    private fun loadInterstitial()
    {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this, getString(R.string.splash_ads1), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
                loadInter2()
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                setInterstitial()
            }
        })
    }

    fun loadInter2()
    {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this, getString(R.string.splash_ads2), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
                loadInter3()
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                setInterstitial()
            }
        })
    }

    fun loadInter3()
    {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this, getString(R.string.splash_ads3), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
                startActivity(Intent(this@BrandActivity,MainActivity::class.java))
                finish()
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                setInterstitial()
            }
        })
    }

    fun setInterstitial()
    {
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                mInterstitialAd = null
                startActivity(Intent(this@BrandActivity,MainActivity::class.java))
                finish()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                mInterstitialAd = null
            }

            override fun onAdShowedFullScreenContent() {
                mInterstitialAd = null
            }
        }

        mInterstitialAd!!.show(this@BrandActivity)
    }
}