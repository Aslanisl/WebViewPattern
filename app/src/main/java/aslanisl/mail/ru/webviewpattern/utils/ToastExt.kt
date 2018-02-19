package aslanisl.mail.ru.webviewpattern.utils

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

fun Toast.showShort(context: Context, text: String) = Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

fun Toast.showShort(context: Context, @StringRes textId: Int) = Toast.makeText(context, textId, Toast.LENGTH_SHORT).show()
