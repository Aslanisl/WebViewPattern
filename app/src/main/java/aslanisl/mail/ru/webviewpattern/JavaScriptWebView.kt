package aslanisl.mail.ru.webviewpattern

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.webkit.*

class  JavaScriptWebView : WebView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var listener: (() -> Unit)? = null

    fun setupWebview(){
        webViewClient = object : WebViewClient(){
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                listener?.invoke()
            }
        }
        webChromeClient = WebChromeClient()
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            setLayerType(2, null)
            settings.allowFileAccessFromFileURLs = true
            settings.allowUniversalAccessFromFileURLs = true
        } else {
            setLayerType(LAYER_TYPE_SOFTWARE, null)
        }
        setBackgroundColor(Color.WHITE)
    }
}