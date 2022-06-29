package com.project.countryholiday

import android.app.Application
import com.project.countryholiday.di.appModule
import com.project.countryholiday.di.holidayModule
import com.project.countryholiday.di.homeModule
import com.project.countryholiday.di.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class CountryHolidayApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(androidContext = this@CountryHolidayApplication)
            modules(
                loginModule,
                appModule,
                homeModule,
                holidayModule
            )
        }
    }
}
