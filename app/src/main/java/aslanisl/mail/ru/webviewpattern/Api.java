package aslanisl.mail.ru.webviewpattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Ivan on 03.02.2018.
 */

public class Api {
    public static final String BASE_URL = "http://centideo.stream/";

    private static volatile ApiService mAPIServiceInstance;

    public static ApiService getApiService() {
        ApiService localInstance = mAPIServiceInstance;
        if (localInstance == null) {
            synchronized (ApiService.class) {
                localInstance = mAPIServiceInstance;
                if (localInstance == null) {
                    Retrofit retrofit = Api.getRetrofit();
                    mAPIServiceInstance = localInstance = retrofit.create(ApiService.class);
                }
            }
        }
        return localInstance;
    }

    static Retrofit getRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (BuildConfig.DEBUG) builder.addInterceptor(interceptor);

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
