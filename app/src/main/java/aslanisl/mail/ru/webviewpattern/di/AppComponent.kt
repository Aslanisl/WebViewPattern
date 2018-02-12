package aslanisl.mail.ru.webviewpattern.di

import aslanisl.mail.ru.webviewpattern.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject (mainActivity: MainActivity)
}