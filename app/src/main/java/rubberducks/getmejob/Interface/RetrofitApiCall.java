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

    @FormUrlEncoded
    @POST(Constants.REGISTRATION)
    Call<JsonObject> userRegister(@Field("name") String name, @Field("mobile") String mobile,@Field("email") String email,@Field("password") String password,@Field("location") String location,@Field("token") String token);

    @FormUrlEncoded
    @POST(Constants.OTP_VERIFY)
    Call<JsonObject> verifyOTP(@Field("mobile") String email, @Field("otp") String password);

    @FormUrlEncoded
    @POST(Constants.FORGET_PASSWORD)
    Call<JsonObject> forgetPassword(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(Constants.RESET_PASSWORD)
    Call<JsonObject> resetPassword(@Field("seekerId") String seekerId,@Field("password") String password);


    @GET(Constants.PREFERRED_LOCATION)
    Call<JsonObject> getPreferredLocation();

}