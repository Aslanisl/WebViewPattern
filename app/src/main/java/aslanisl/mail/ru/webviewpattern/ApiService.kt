package aslanisl.mail.ru.webviewpattern

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/get/index.html")
    fun getData() : Call<String>
}
