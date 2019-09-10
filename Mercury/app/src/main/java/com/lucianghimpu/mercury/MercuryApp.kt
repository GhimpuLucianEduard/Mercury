package com.lucianghimpu.mercury

import android.app.Application
import com.lucianghimpu.mercury.DI.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MercuryApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MercuryApp)
            modules(appModule)
        }
    }
}