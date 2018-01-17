package dk.momondo.momondolight

import android.app.Application
import timber.log.Timber


class MomondoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}