package aslanisl.mail.ru.webviewpattern

import android.content.Context
import android.support.annotation.AttrRes
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

class JavaScriptWebView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr){

    init {
        webViewClient = WebViewClient()
        webChromeClient = WebChromeClient()
        settings.javaScriptEnabled = true
    }
}