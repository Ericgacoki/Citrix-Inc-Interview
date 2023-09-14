package com.ericdev.citrixincinterview.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CitrixApp  : Application() {

    /** Start Timber and Hilt as soon as the app launches
    * */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
