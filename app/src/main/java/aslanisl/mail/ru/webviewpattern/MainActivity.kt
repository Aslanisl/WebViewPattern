package aslanisl.mail.ru.webviewpattern

import android.os.Bundle
import android.widget.Toast
import aslanisl.mail.ru.webviewpattern.utils.gone
import aslanisl.mail.ru.webviewpattern.utils.showShort
import aslanisl.mail.ru.webviewpattern.utils.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BackPressedActivity() {

    private lateinit var webView: JavaScriptWebView

    private val url = "http://coinbugg.win/dl20dlDgsl"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        webView.setupWebview()
        tryAgain.setOnClickListener { loadWebview() }
        webView.listener = {
            tryAgain.visible()
            webView.gone()
            Toast(this).showShort(this, R.string.load_again_error)
        }
        loadWebview()
    }

    private fun loadWebview(){
        tryAgain.gone()
        webView.visible()
        webView.loadUrl(url)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
