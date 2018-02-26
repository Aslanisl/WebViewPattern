package aslanisl.mail.ru.webviewpattern.launch

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import aslanisl.mail.ru.webviewpattern.GlideApp
import aslanisl.mail.ru.webviewpattern.webview.MainActivity
import aslanisl.mail.ru.webviewpattern.R
import aslanisl.mail.ru.webviewpattern.custom.InternetStatusView
import aslanisl.mail.ru.webviewpattern.utils.gone
import aslanisl.mail.ru.webviewpattern.utils.invisible
import aslanisl.mail.ru.webviewpattern.utils.visible
import de.adorsys.android.finger.Finger
import de.adorsys.android.finger.FingerListener
import kotlinx.android.synthetic.main.fragment_launch.*
import android.support.v7.widget.RecyclerView.ViewHolder
import android.support.annotation.NonNull
import com.gdacciaro.iOSDialog.iOSDialogBuilder
import de.blox.treeview.BaseTreeAdapter
import de.blox.treeview.TreeView



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
        val rootView = inflater.inflate(R.layout.fragment_launch, container, false)

        val loadingImage = rootView.findViewById<ImageView>(R.id.loadingImage)
        GlideApp.with(context).load(R.drawable.giphy).into(loadingImage)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        internetStatusView.setStatusListener(object :InternetStatusView.OnStatusListener{
            override fun connected() {
                changeState(true)
            }

            override fun disconnected() {
                changeState(false)
            }
        })

        settingsView.setOnClickListener { startSettings() }

        //Do some staff
        val currentTime = System.currentTimeMillis()
        if (currentTime > System.currentTimeMillis()) {
            val finger = Finger(context!!)
            finger.subscribe(object : FingerListener {
                override fun onFingerprintAuthenticationSuccess() {
                    // The user authenticated successfully -> go on with your logic
                }

                override fun onFingerprintAuthenticationFailure(errorMessage: String, errorCode: Int) {
                    // Show the user the human readable error message and use the error code if necessary
                    // and subscribe again
                }

                override fun onFingerprintLockoutReleased() {
                    // react in ui --> tell the user that he/she can try again
                    // and subscribe again
                }
            })


            val treeView = TreeView(context)
            treeView.lineColor = Color.BLACK

            val iosDialog = iOSDialogBuilder(context).setTitle("TEST").build()
            iosDialog.show()
        }


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