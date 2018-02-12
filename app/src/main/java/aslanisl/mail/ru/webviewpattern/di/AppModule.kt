package aslanisl.mail.ru.webviewpattern.di

import aslanisl.mail.ru.webviewpattern.ApiService
import aslanisl.mail.ru.webviewpattern.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providerRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        if (BuildConfig.DEBUG) builder.addInterceptor(interceptor)

        return Retrofit.Builder()
                .baseUrl("http://www.appanalytics.host/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providerApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}