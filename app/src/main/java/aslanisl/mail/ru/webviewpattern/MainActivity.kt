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
        webview.loadUrl("http://m66e085.winfortuna.com/?lp=rp&trackCode=aff_7e89ac_11_ta7&pid=ta")
//        if (fromAssets) {
//            webview.loadUrl("file:///android_asset/index.html")
//        } else {
//            webview.loadUrl(getString(R.string.url))
//        }
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
