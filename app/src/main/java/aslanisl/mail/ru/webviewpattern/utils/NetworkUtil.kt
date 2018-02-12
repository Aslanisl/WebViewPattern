package aslanisl.mail.ru.webviewpattern.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtil {

    companion object {

        fun isConnected(context: Context) : Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm?.activeNetworkInfo?.isConnectedOrConnecting ?: false
        }
    }
}