package com.itsm.misantecosunidos.data.model

import android.app.Application

class Application: Application() {
    companion object {
        lateinit var prefs: UserPrefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = UserPrefs(applicationContext)
    }
}