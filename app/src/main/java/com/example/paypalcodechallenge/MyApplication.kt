package com.example.paypalcodechallenge

import android.app.Application
import com.example.paypalcodechallenge.domain.SharedPreferencesManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)
    }
}
