package aslanisl.mail.ru.webviewpattern

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import aslanisl.mail.ru.webviewpattern.utils.bind
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    var call: Disposable? = null

    private val webview by bind<WebView>(R.id.webview)

    private var response: String? = null

    @Inject lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.getAppComponent().inject(this)

        if (savedInstanceState == null || response == null) {

            call = apiService.getData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        if (response != null) {
                            this.response = response

                            webview.webViewClient = WebViewClient()
                            webview.webChromeClient = WebChromeClient()
                            val webSettings = webview.settings
                            webSettings.javaScriptEnabled = true

                            if (response.equals("1")) {
                                webview.loadUrl(getString(R.string.url))
                            } else {
                                webview.loadUrl("file:///android_asset/index.html")
                            }
                        }
                    }, { failure -> Log.d("TAG", failure.message) })

        } else {
            webview.restoreState(savedInstanceState)
        }
    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        webview.saveState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        call?.dispose()
    }
}
