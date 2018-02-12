package aslanisl.mail.ru.webviewpattern

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/tracking/")
    fun getData() : Single<String>

}
