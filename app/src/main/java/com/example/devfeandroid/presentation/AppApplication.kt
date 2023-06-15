package com.example.devfeandroid.presentation

import android.app.Application
import com.example.devfeandroid.extensions.setApplication

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setApplication(this)
    }
}
