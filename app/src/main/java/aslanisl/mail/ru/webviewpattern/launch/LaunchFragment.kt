package aslanisl.mail.ru.webviewpattern.launch

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aslanisl.mail.ru.webviewpattern.App
import aslanisl.mail.ru.webviewpattern.GlideApp
import aslanisl.mail.ru.webviewpattern.MainActivity
import aslanisl.mail.ru.webviewpattern.R
import aslanisl.mail.ru.webviewpattern.trash.TrashDatabase
import aslanisl.mail.ru.webviewpattern.trash.TrashModel
import aslanisl.mail.ru.webviewpattern.utils.NetworkUtil
import aslanisl.mail.ru.webviewpattern.utils.gone
import aslanisl.mail.ru.webviewpattern.utils.invisible
import aslanisl.mail.ru.webviewpattern.utils.visible
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.shuhart.stepview.StepView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_launch.*


class LaunchFragment : Fragment() {

    companion object {
        val TAG = LaunchFragment::class.java.simpleName

        fun newInstance() = LaunchFragment()
    }

    private lateinit var launchViewModel: LaunchViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchViewModel = ViewModelProviders.of(this).get(LaunchViewModel::class.java)
        launchViewModel.init(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_launch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlideApp.with(context).load(R.drawable.loader).into(loadingImage)

        //Do some staff
        val currentTime = System.currentTimeMillis()
        if (currentTime > System.currentTimeMillis()) {
            val appDatabase = Room.databaseBuilder(context!!, TrashDatabase::class.java, "my_db").build()
            val trashDao = appDatabase.trashDao()
            trashDao.insert(TrashModel("Some String"))

            val preference = BinaryPreferencesBuilder(App.getAppContext())
                    .name("some_name")
                    .build()
            preference.edit().putString("KEY", "STRING").apply()

            val stepView = StepView(context)
            Log.d("TAG", "$stepView.currentStep")

            Picasso.with(context).load("https://static.pexels.com/photos/207962/pexels-photo-207962.jpeg").into(loadingImage)

            frescoImage.setImageURI("https://raw.githubusercontent.com/facebook/fresco/master/docs/static/logo.png")
        }

        settingsView.setOnClickListener { startSettings() }
        context?.registerReceiver(connectedReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        launchViewModel.getStatusData().observe(this, Observer<Boolean> {
            response -> response?.let { if (it) {
                MainActivity.startActivityAsTop(context!!)
                activity?.finish()
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        changeState()
    }

    private fun startSettings() {
        val intent = Intent(Settings.ACTION_SETTINGS)
        startActivity(intent)
    }

    private fun changeState() {
        if (currentState == State.CONNECTED) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        context?.unregisterReceiver(connectedReceiver)
        App.refWatcher?.watch(this)
    }
}