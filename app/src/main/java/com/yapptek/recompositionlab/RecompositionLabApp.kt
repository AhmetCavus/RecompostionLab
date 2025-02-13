package com.yapptek.recompositionlab

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecompositionLabApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
