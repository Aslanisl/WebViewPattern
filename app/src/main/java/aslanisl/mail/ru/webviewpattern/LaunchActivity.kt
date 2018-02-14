package aslanisl.mail.ru.webviewpattern

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.sqlite.SQLiteDatabase
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import aslanisl.mail.ru.webviewpattern.utils.NetworkUtil
import aslanisl.mail.ru.webviewpattern.utils.gone
import aslanisl.mail.ru.webviewpattern.utils.visible
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.mikepenz.materialdrawer.DrawerBuilder
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_launch.*
import java.sql.SQLException

class LaunchActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private var snackbar: Snackbar? = null

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

        GlideApp.with(this).load(R.drawable.loading_spinner).into(loadingImage)

        //Do some staff
        val currentTime = System.currentTimeMillis();
        if (currentTime > System.currentTimeMillis()) {
            //Load drawer
            val drawer = DrawerBuilder().withActivity(this).build()
            drawer.closeDrawer()

            //Load sql lite
            val dataBase = DataBase(this)
            dataBase.isOpen

            //Load realm
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            realm.commitTransaction()

            //Load yandex map
            val apiKey = mapView.apiKey
            val mapController = mapView.mapController
            mapView.showJamsButton(true)
        }

        snackbar = Snackbar.make(container, R.string.internet_disconnected, Snackbar.LENGTH_INDEFINITE).setAction(R.string.settings_internet, this::startSettings)

        handler = Handler()
        runnable = Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerReceiver(connectedReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun startSettings(view: View) {
        val intent = Intent(Settings.ACTION_SETTINGS)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        changeState()
    }

    inner class DataBase(val context: Context) : OrmLiteSqliteOpenHelper(this, "name",null, 1){

        override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
            try {
                TableUtils.createTable(connectionSource, String::class.java)
            } catch (e: SQLException){
            }
        }

        override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
            try {
                TableUtils.createTable(connectionSource, String::class.java)
            } catch (e: SQLException){
            }
        }
    }

    private fun changeState() {
        if (currentState == State.CONNECTED) {
            handler.postDelayed(runnable, (3 * 1000).toLong())
            loadingImage.visible()
            snackbar?.dismiss()
        } else {
            handler.removeCallbacks(runnable)
            loadingImage.gone()
            snackbar?.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectedReceiver)
        handler.removeCallbacks(runnable)
    }
}