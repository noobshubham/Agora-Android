package org.aossie.agoraandroid

import android.app.Application
import android.util.Log
import com.yariksoffice.lingver.Lingver
import org.aossie.agoraandroid.ui.di.AppComponent
import org.aossie.agoraandroid.ui.di.DaggerAppComponent
import org.aossie.agoraandroid.utilities.AppConstants
import timber.log.Timber
import timber.log.Timber.DebugTree
import timber.log.Timber.Tree

class AgoraApp : Application() {

  init {
    System.setProperty(
      "org.apache.poi.javax.xml.stream.XMLInputFactory",
      "com.fasterxml.aalto.stax.InputFactoryImpl"
    )
    System.setProperty(
      "org.apache.poi.javax.xml.stream.XMLOutputFactory",
      "com.fasterxml.aalto.stax.OutputFactoryImpl"
    )
    System.setProperty(
      "org.apache.poi.javax.xml.stream.XMLEventFactory",
      "com.fasterxml.aalto.stax.EventFactoryImpl"
    )
  }

  val appComponent: AppComponent by lazy {
    DaggerAppComponent.factory()
      .create(applicationContext)
  }

  override fun onCreate() {
    super.onCreate()
    Timber.plant(if (BuildConfig.DEBUG) DebugTree() else CrashReportingTree())
    Lingver.init(this,AppConstants.DEFAULT_LANG)
  }

  private class CrashReportingTree : Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
      return priority >= Log.ERROR
    }
    // Log Exception to Crashlytics here
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
    }
  }
}
