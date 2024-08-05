package com.solodilov.speedtestapp.app

import android.app.Application
import com.solodilov.data.di.dataModule
import com.solodilov.domain.di.domainModule
import com.solodilov.presentation.di.presentationModule
import com.solodilov.speedtestapp.di.mainModule
import com.solodilov.util.di.utilModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    dataModule,
                    domainModule,
                    presentationModule,
                    mainModule,
                    utilModule,
                )
            )
        }
    }
}