package aslanisl.mail.ru.webviewpattern

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.yandex.metrica.YandexMetrica

class App : Application() {

    private val YANDEX_METRICA_KEY = ""

    companion object {
        private lateinit var INSTANCE: App

        var refWatcher: RefWatcher? = null

        fun getAppContext() = INSTANCE.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        if (!YANDEX_METRICA_KEY.isNullOrEmpty()) {
            YandexMetrica.activate(applicationContext, YANDEX_METRICA_KEY)
            YandexMetrica.enableActivityAutoTracking(this)
        }

        if (BuildConfig.DEBUG){
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return
            }
            refWatcher = LeakCanary.install(this)
        }

        Fresco.initialize(this)
    }
}