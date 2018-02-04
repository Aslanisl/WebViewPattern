package aslanisl.mail.ru.webviewpattern;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Disposable call;

    @BindView(R.id.webview) WebView webview;

    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null || response == null) {

            call = Api.getApiService().getResponse()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response != null) {
                            this.response = response;

                            webview.setWebViewClient(new WebViewClient());
                            webview.setWebChromeClient(new WebChromeClient());
                            WebSettings webSettings = webview.getSettings();
                            webSettings.setJavaScriptEnabled(true);

                            if (response.equals("1")){
                                webview.loadUrl("https://www.google.ru/");
                            } else {
                                webview.loadUrl("https://www.yandex.ru/");
                            }
                        }
                    }, failure -> {
                        Log.d("TAG", failure.getMessage());
                    });

        } else {
            webview.restoreState(savedInstanceState);
        }
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webview.saveState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null) call.dispose();
    }
}
