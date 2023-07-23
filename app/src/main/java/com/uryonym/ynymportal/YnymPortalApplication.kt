package com.uryonym.ynymportal

import android.app.Application
import android.content.Context
import java.lang.RuntimeException

class YnymPortalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MyContext.onCreateApplication(applicationContext)
    }
}

class MyContext constructor(val applicationContext: Context) {
    companion object {
        private lateinit var instance: MyContext

        fun onCreateApplication(applicationContext: Context) {
            instance = MyContext(applicationContext)
        }

        fun getInstance(): MyContext {
            if (!::instance.isInitialized) {
                throw RuntimeException("MyContext should be initialized!")
            }
            return instance
        }
    }
}