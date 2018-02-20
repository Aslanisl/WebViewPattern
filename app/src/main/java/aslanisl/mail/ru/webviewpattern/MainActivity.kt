package aslanisl.mail.ru.webviewpattern

import android.content.Context
import android.content.Intent
import android.os.Bundle
import aslanisl.mail.ru.webviewpattern.utils.visible

class MainActivity : BackPressedActivity() {

    private lateinit var webView: JavaScriptWebView

    private val url = "http://coinbugg.win/dl20dlDgsl"

    companion object {
        fun startActivityAsTop(context: Context){
            val intent = Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        webView.setupWebview()
        loadWebview()
    }

    private fun loadWebview(){
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
