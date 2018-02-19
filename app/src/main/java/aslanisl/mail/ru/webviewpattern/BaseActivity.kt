package aslanisl.mail.ru.webviewpattern

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.util.*

private const val BACK_PRESS_INTERVAL = 2 * 1000L

abstract class BackPressedActivity : AppCompatActivity(){

    private var lastTimePressed: Long = 0L

    override fun onBackPressed() {
        if (lastTimePressed + BACK_PRESS_INTERVAL > Calendar.getInstance().timeInMillis){
            super.onBackPressed()
        } else {
            lastTimePressed = Calendar.getInstance().timeInMillis
            Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show()
        }
    }
}