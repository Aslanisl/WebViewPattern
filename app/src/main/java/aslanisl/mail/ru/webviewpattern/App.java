package aslanisl.mail.ru.webviewpattern;

import android.app.Application;

import com.twitter.sdk.android.core.Twitter;

/**
 * Created by Ivan on 06.02.2018.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Twitter.initialize(this);
    }
}
