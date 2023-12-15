package com.example.tbc_better_login_davaleba17

import android.app.Application

class MyApplication : Application() {
    companion object{
        lateinit var application: Application
            private set
    }
    override fun onCreate() {
        super.onCreate()
        application = this
    }
}