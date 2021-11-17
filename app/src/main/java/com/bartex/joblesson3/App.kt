package com.bartex.joblesson3

import android.app.Application

class App: Application() {
    companion object{
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}