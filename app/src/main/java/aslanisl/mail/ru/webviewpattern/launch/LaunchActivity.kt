package aslanisl.mail.ru.webviewpattern.launch

import android.os.Bundle
import aslanisl.mail.ru.webviewpattern.BackPressedActivity
import aslanisl.mail.ru.webviewpattern.R

class LaunchActivity : BackPressedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LaunchFragment.newInstance(), LaunchFragment.TAG)
                .commitAllowingStateLoss()
    }
}