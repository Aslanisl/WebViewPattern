package aslanisl.mail.ru.webviewpattern.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import aslanisl.mail.ru.webviewpattern.BackPressedActivity
import aslanisl.mail.ru.webviewpattern.R

class MainActivity : BackPressedActivity() {

    companion object {
        fun startActivityAsTop(context: Context){
            val intent = Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    private lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFragment = MainFragment.newInstance()

        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, mainFragment, MainFragment.TAG)
                .commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        if (mainFragment.finishFragment()){
            super.onBackPressed()
        }
    }
}
