/*
 * Copyright (c) 2021. Ryan Wong (hello@ryanwong.co.uk)
 *
 */

package uk.ryanwong.dazn.codechallenge

import android.app.Application
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant

@HiltAndroidApp
class DaznApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
            Timber.d("Using debug tree - this is a debug build")
        } else {
            plant(CrashReportingTree())
            Timber.d("Using CrashReporting tree - if you see this it means something is wrong")
        }
    }

    /** A tree which logs only the important information on Crashlytics for crash reporting.  */
    inner class CrashReportingTree : Timber.Tree() {
        private val priority = "priority"
        private val tag = "tag"
        private val message = "message"
        private val crashlytics = FirebaseCrashlytics.getInstance()

        override fun log(
            priority: Int,
            tag: String?,
            message: String,
            t: Throwable?,
        ) {
            when (priority) {
                Log.VERBOSE, Log.DEBUG, Log.INFO -> return

                else -> {
                    // Custom keys help you get the specific state of your app leading up to a crash.
                    // You can associate arbitrary key/value pairs with your crash reports,
                    // then use the custom keys to search and filter crash reports in the Firebase console.
                    crashlytics.setCustomKey(this.priority, priority)
                    tag?.let { crashlytics.setCustomKey(this.tag, it) }
                    crashlytics.setCustomKey(this.message, message)

                    if (t == null) {
                        FirebaseCrashlytics.getInstance().recordException(Exception(message))
                    } else {
                        FirebaseCrashlytics.getInstance().recordException(t)
                    }
                }
            }
        }
    }
}
