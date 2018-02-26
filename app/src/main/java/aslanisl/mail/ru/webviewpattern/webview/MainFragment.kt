package aslanisl.mail.ru.webviewpattern.webview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aslanisl.mail.ru.webviewpattern.JavaScriptWebView
import aslanisl.mail.ru.webviewpattern.R
import aslanisl.mail.ru.webviewpattern.utils.visible

class MainFragment : Fragment() {

    private lateinit var webView: JavaScriptWebView

    private val url = "http://jfjutyyuty.club/9tBMYT"

    companion object {
        val TAG = MainFragment::class.java.simpleName

        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView = view.findViewById(R.id.webview)
        webView.setupWebview()
        loadWebview()
    }

    private fun loadWebview() {
        webView.visible()
        webView.loadUrl(url)
    }

    fun finishFragment() : Boolean {
        if (webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return false
    }
}