package rubberducks.getmejob.Controller;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rahul on 28/9/17.
 */

public class BasicAuthInterceptor implements Interceptor {
    private String authToken;

    public BasicAuthInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("key", authToken).build();
        return chain.proceed(authenticatedRequest);
    }


}
