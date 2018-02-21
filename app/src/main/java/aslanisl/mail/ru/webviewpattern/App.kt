package aslanisl.mail.ru.webviewpattern

import android.app.Application
import com.yandex.metrica.YandexMetrica

class App : Application() {

    private val YANDEX_METRICA_KEY = ""

    companion object {
        private lateinit var INSTANCE: App

        fun getAppContext() = INSTANCE.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        if (!YANDEX_METRICA_KEY.isNullOrEmpty()) {
            YandexMetrica.activate(applicationContext, YANDEX_METRICA_KEY)
            YandexMetrica.enableActivityAutoTracking(this)
        }
    }
}