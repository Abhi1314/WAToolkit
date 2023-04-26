package com.example.pistalix.watoolkit.utils

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.orhanobut.hawk.Hawk

class MainApplication : Application()
{

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}