package aslanisl.mail.ru.webviewpattern;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by Ivan on 03.02.2018.
 */

public interface ApiService {
    @GET("/tracking/")
    Single<String> getResponse();
}
