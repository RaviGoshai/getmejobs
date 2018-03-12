package rubberducks.getmejob.Interface;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;


/**
 * Created by ADMIN on 2/13/2018.
 */

public interface RetrofitApiCall {

    @FormUrlEncoded
    @POST(Constants.LOGIN)
    Call<Constants> login(@Field("email") String email, @Field("password") String password);

    @GET(Constants.PREFERRED_LOCATION)
    Call<JsonObject> getPreferredLocation();

}