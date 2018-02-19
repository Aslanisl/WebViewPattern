package aslanisl.mail.ru.webviewpattern

import android.os.Bundle

class MainActivity : BackPressedActivity() {

    private lateinit var webView: JavaScriptWebView

    private val url = "http://chatlounge.stream/glsr5lsf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        webView.setupWebview()
        webView.listener = { webView.loadUrl(url) }
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
