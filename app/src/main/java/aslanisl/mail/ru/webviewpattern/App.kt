package aslanisl.mail.ru.webviewpattern

import android.app.Application
import aslanisl.mail.ru.webviewpattern.di.AppComponent
import aslanisl.mail.ru.webviewpattern.di.DaggerAppComponent
import com.twitter.sdk.android.core.Twitter

class App : Application() {

    companion object {
        private var diComponent: AppComponent? = null

        fun getAppComponent() : AppComponent {
            if (diComponent == null){
                diComponent = DaggerAppComponent.builder().build()
            }
            return diComponent as AppComponent
        }
    }

    override fun onCreate() {
        super.onCreate()

        Twitter.initialize(this)
    }
}