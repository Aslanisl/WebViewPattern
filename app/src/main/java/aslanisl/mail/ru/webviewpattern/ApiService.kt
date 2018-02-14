package aslanisl.mail.ru.webviewpattern

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/tracking/")
    fun getData() : Call<String>
}
