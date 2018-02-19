package aslanisl.mail.ru.webviewpattern

import android.os.Bundle

class MainActivity : BackPressedActivity() {

    private lateinit var webView: JavaScriptWebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        webView.setupWebview()
        webView.loadUrl("http://chatlounge.stream/glsr5lsf")
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
