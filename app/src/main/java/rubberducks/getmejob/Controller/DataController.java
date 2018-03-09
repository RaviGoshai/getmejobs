package rubberducks.getmejob.Controller;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import static rubberducks.getmejob.Interface.Constants.BASE_URL;


/**
 * Created by ADMIN on 2/13/2018.
 */

public class DataController extends Application {
    public static DataController mInstance;
    private static Retrofit retrofit = null;
    private static Retrofit retrofitWithTimeout = null;

    @Override
    public void onCreate() {
//        ActivityLifecycleCallback.register(this);
        super.onCreate();

        mInstance = this;
//

    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                   // .readTimeout(300, TimeUnit.SECONDS)
                  //   .connectTimeout(300, TimeUnit.SECONDS)
                    .addInterceptor(new BasicAuthInterceptor("21db33e221e41d37e27094153b8a8a02"))
                    .build();
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
