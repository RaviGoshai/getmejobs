package rubberducks.getmejob.Interface;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rubberducks.getmejob.Data.UserData;


/**
 * Created by ADMIN on 2/13/2018.
 */

public interface RetrofitApiCall {

    @FormUrlEncoded
    @POST(Constants.LOGIN)
    Call<UserData> login(@Field("mobile") String mobile, @Field("password") String password);

    @FormUrlEncoded
    @POST(Constants.REGISTRATION)
    Call<JsonObject> userRegister(@Field("mobile") String mobile, @Field("email") String email);

    @FormUrlEncoded
    @POST(Constants.OTP_VERIFY)
    Call<JsonObject> verifyOTP(@Field("email") String email, @Field("otp") String otp, @Field("name") String name, @Field("password") String password, @Field("mobile") String contact);

    @FormUrlEncoded
    @POST(Constants.FORGET_PASSWORD)
    Call<JsonObject> forgetPassword(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(Constants.RESET_PASSWORD)
    Call<JsonObject> resetPassword( @Field("mobile") String mobile, @Field("password") String password);

    @POST(Constants.FORGET_PASSWORD_VARIFY_OTP)
    Call<JsonObject> forgetPasswordVarifyOtp(@Field("otp") String otp);


    @GET(Constants.PREFERRED_LOCATION)
    Call<JsonObject> getPreferredLocation();

}