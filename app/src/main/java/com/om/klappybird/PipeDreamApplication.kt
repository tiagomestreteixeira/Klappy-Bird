package com.om.klappybird

import android.app.Application
import timber.log.Timber

class PipeDreamApplication: Application() {
  override fun onCreate() {
    super.onCreate()

    Timber.plant(Timber.DebugTree())
  }
}