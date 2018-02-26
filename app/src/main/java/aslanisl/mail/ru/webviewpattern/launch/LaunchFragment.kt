package aslanisl.mail.ru.webviewpattern.launch

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aslanisl.mail.ru.webviewpattern.GlideApp
import aslanisl.mail.ru.webviewpattern.webview.MainActivity
import aslanisl.mail.ru.webviewpattern.R
import aslanisl.mail.ru.webviewpattern.custom.InternetStatusView
import aslanisl.mail.ru.webviewpattern.utils.gone
import aslanisl.mail.ru.webviewpattern.utils.invisible
import aslanisl.mail.ru.webviewpattern.utils.visible
import kotlinx.android.synthetic.main.fragment_launch.*

class LaunchFragment : Fragment() {

    companion object {
        val TAG = LaunchFragment::class.java.simpleName

        fun newInstance() = LaunchFragment()
    }

    private lateinit var launchViewModel: LaunchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchViewModel = ViewModelProviders.of(this).get(LaunchViewModel::class.java)
        launchViewModel.init(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val internetStatusView = InternetStatusView(context!!)
        internetStatusView.setStatusListener(object :InternetStatusView.OnStatusListener{
            override fun connected() {
                changeState(true)
            }

            override fun disconnected() {
                changeState(false)
            }
        })

        return inflater.inflate(R.layout.fragment_launch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlideApp.with(context).load(R.drawable.loading_gi).into(loadingImage)

        //Do some staff
        val currentTime = System.currentTimeMillis()
        if (currentTime > System.currentTimeMillis()) {

        }

        settingsView.setOnClickListener { startSettings() }

        launchViewModel.getStatusData().observe(this, Observer<Boolean> {
            response -> response?.let { if (it) {
                MainActivity.startActivityAsTop(context!!)
                activity?.finish()
                }
            }
        })
    }

    private fun startSettings() {
        val intent = Intent(Settings.ACTION_SETTINGS)
        startActivity(intent)
    }

    private fun changeState(connected: Boolean) {
        if (connected) {
            loadingImage.visible()
            settingsView.invisible()
            internetStatus.gone()
            launchViewModel.load()
        } else {
            loadingImage.gone()
            settingsView.visible()
            internetStatus.visible()
            launchViewModel.stopLoad()
        }
    }
}