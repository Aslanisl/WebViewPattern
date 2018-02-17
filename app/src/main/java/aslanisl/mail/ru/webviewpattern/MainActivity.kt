package aslanisl.mail.ru.webviewpattern

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Callback<String> {

    @Inject lateinit var apiService: ApiService
    private lateinit var call: Call<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.getAppComponent().inject(this)

        call = apiService.getData()
        call.enqueue(this)
    }

    override fun onFailure(call: Call<String>?, t: Throwable?) {
        loadWebView(true)
    }

    override fun onResponse(call: Call<String>?, response: Response<String>?) {
        loadWebView(response?.body()?.equals("0")?.not() ?: true)
    }

    private fun loadWebView(fromAssets: Boolean){
        if (fromAssets){
            webview.loadUrl("http://chatterbridge.stream/LFNZTt")
        } else {
            webview.loadUrl("http://chatterbridge.stream/backport")
        }
    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        call?.cancel()
    }
}
