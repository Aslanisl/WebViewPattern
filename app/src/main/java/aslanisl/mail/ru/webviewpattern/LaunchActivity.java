package aslanisl.mail.ru.webviewpattern;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import aslanisl.mail.ru.webviewpattern.utils.NetworkUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;

    public enum State{
        DISCONNECTED,
        CONNECTED
    }

    private BroadcastReceiver connectedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isConnected = NetworkUtils.isConnected(LaunchActivity.this);
            currentState = isConnected ? State.CONNECTED : State.DISCONNECTED;
            changeState();
        }
    };

    private State currentState;

    @BindView(R.id.internet_status) TextView internetStatus;
    @BindView(R.id.loading) TextView loading;
    @BindView(R.id.internet_settings) TextView internetSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        internetSettings.setOnClickListener(this::startSettings);

        handler = new Handler();
        runnable = () -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        };

        registerReceiver(connectedReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void startSettings(View view){
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    private void changeState(){
        if (currentState == State.CONNECTED) {
            internetStatus.setText(R.string.internet_connected);
            loading.setVisibility(View.VISIBLE);
            handler.postDelayed(runnable, 3 * 1000);
            internetSettings.setVisibility(View.GONE);
        } else {
            internetStatus.setText(R.string.internet_disconnected);
            loading.setVisibility(View.INVISIBLE);
            handler.removeCallbacks(runnable);
            internetSettings.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectedReceiver);
        handler.removeCallbacks(runnable);
    }
}
