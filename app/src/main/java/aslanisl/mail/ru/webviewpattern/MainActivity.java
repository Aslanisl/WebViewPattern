package aslanisl.mail.ru.webviewpattern;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Disposable call;

    @BindView(R.id.webview) WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {

            call = Api.getApiService().getResponse()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response != null) {
                            setUpWebview();

                            if (response.equals("1")){
                                webview.loadUrl("http://centideo.stream/svfcpf");
                            } else {
                                webview.loadUrl("http://centideo.stream/backport");
                            }
                        }
                    }, failure -> {
                        Log.d("TAG", failure.getMessage());
                    });

        } else {
            webview.restoreState(savedInstanceState);
        }
    }

    private void setUpWebview(){
        webview.setWebViewClient(new WebViewClient());
        webview.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            webview.setLayerType(2, null);
            webSettings.setAllowFileAccess(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        } else {
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webview.setBackgroundColor(Color.WHITE);
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
