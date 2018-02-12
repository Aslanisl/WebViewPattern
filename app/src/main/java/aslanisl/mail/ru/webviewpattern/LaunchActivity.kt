package aslanisl.mail.ru.webviewpattern

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import aslanisl.mail.ru.webviewpattern.utils.*

class LaunchActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private val connectedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val isConnected = NetworkUtil.isConnected(this@LaunchActivity)
            currentState = if (isConnected) State.CONNECTED else State.DISCONNECTED
            changeState()
        }
    }

    private var currentState: State = State.DISCONNECTED

    private val internetStatus by bind<TextView>(R.id.internet_status)
    private val loading by bind<TextView>(R.id.loading)
    private val internetSettings by bind<TextView>(R.id.internet_settings)
    private val loadingProgress by bind<ProgressBar>(R.id.loading_progress)

    enum class State {
        DISCONNECTED,
        CONNECTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        internetSettings.setOnClickListener { startSettings() }

        handler = Handler()
        runnable = Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerReceiver(connectedReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun startSettings() {
        val intent = Intent(Settings.ACTION_SETTINGS)
        startActivity(intent)
    }

    private fun changeState() {
        if (currentState == State.CONNECTED) {
            internetStatus.setText(R.string.internet_connected)
            loading.visible()
            loadingProgress.visible()
            handler.postDelayed(runnable, (3 * 1000).toLong())
            internetSettings.gone()
        } else {
            internetStatus.setText(R.string.internet_disconnected)
            loading.invisible()
            loadingProgress.invisible()
            handler.removeCallbacks(runnable)
            internetSettings.visible()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectedReceiver)
        handler.removeCallbacks(runnable)
    }
}