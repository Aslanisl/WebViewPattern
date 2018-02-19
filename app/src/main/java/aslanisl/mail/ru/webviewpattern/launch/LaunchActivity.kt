package aslanisl.mail.ru.webviewpattern.launch

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import aslanisl.mail.ru.webviewpattern.BackPressedActivity
import aslanisl.mail.ru.webviewpattern.GlideApp
import aslanisl.mail.ru.webviewpattern.MainActivity
import aslanisl.mail.ru.webviewpattern.R
import aslanisl.mail.ru.webviewpattern.utils.NetworkUtil
import aslanisl.mail.ru.webviewpattern.utils.gone
import aslanisl.mail.ru.webviewpattern.utils.visible
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : BackPressedActivity() {

    private lateinit var launchViewModel: LaunchViewModel

    private val connectedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val isConnected = NetworkUtil.isConnected(this@LaunchActivity)
            currentState = if (isConnected) State.CONNECTED else State.DISCONNECTED
            changeState()
        }
    }

    private var currentState: State = State.DISCONNECTED

    enum class State {
        DISCONNECTED,
        CONNECTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        GlideApp.with(this).load(R.drawable.loading_animation).into(loadingImage)
        launchViewModel = ViewModelProviders.of(this).get(LaunchViewModel::class.java)
        launchViewModel.init(this)

        //Do some staff
        val currentTime = System.currentTimeMillis();
        if (currentTime > System.currentTimeMillis()) {

        }

        settingsView.setOnClickListener(this::startSettings)
        registerReceiver(connectedReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        launchViewModel.getStatusData().observe(this, object: Observer<Boolean> {
            override fun onChanged(response: Boolean?) {
                response?.let { if (it) startActivity() }
            }
        })
    }

    private fun startActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startSettings(view: View) {
        val intent = Intent(Settings.ACTION_SETTINGS)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        changeState()
    }

    private fun changeState() {
        if (currentState == State.CONNECTED) {
            loadingImage.visible()
            settingsView.gone()
            internetStatus.gone()
            launchViewModel.load()
        } else {
            loadingImage.gone()
            settingsView.visible()
            internetStatus.visible()
            launchViewModel.stopLoad()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectedReceiver)
    }
}