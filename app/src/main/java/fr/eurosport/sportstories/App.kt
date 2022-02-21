package fr.eurosport.sportstories

import android.app.Application
import fr.eurosport.sportstories.feature_media.di.appModule
import fr.eurosport.sportstories.feature_media.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    viewModelModule
                )
            )
        }
    }
}