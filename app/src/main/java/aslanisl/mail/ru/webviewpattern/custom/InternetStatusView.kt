package aslanisl.mail.ru.webviewpattern.custom

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.AttributeSet
import android.view.View
import aslanisl.mail.ru.webviewpattern.utils.NetworkUtil

class InternetStatusView : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val connectedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val isConnected = NetworkUtil.isConnected(context)
            currentState = if (isConnected) State.CONNECTED else State.DISCONNECTED
            changeState()
        }
    }

    private var currentState: State = State.DISCONNECTED

    enum class State {
        DISCONNECTED,
        CONNECTED
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        context.registerReceiver(connectedReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        statusListener = null
        context.unregisterReceiver(connectedReceiver)
    }

    interface OnStatusListener {
        fun disconnected()
        fun connected()
    }

    private var statusListener: OnStatusListener? = null

    fun setStatusListener(statusListener: OnStatusListener) {
        this.statusListener = statusListener
    }

    private fun changeState() {
        if (currentState == State.CONNECTED) {
            statusListener?.connected()
        } else {
            statusListener?.disconnected()
        }
    }

}