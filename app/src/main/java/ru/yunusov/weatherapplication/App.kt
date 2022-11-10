package ru.yunusov.weatherapplication

import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = WeakReference(this)
    }

    companion object {
        private var context: WeakReference<Context>? = null
        fun getAppContext(): Context? {
            return context?.get()
        }
    }
}
