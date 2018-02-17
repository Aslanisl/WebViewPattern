package aslanisl.mail.ru.webviewpattern

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/get/index.html")
    fun getData() : Single<String>

}
