package rubberducks.getmejob.Registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.JsonObject;
import com.mukesh.OtpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rubberducks.getmejob.Controller.DataController;
import rubberducks.getmejob.Interface.Constants;
import rubberducks.getmejob.Interface.RetrofitApiCall;
import rubberducks.getmejob.R;
import rubberducks.getmejob.Utils.LoaderDialog;
import rubberducks.getmejob.Utils.NetworkAvailability;
import rubberducks.getmejob.Utils.SnakeAlert;

public class VerifyOTP extends AppCompatActivity implements View.OnClickListener{
    private Toolbar myToolbar;
    private ImageView backBtn;
    private OtpView otpView;
    private Button resendOtp;
    private Button submit;
    private Button nextVerifyButton;
    private LoaderDialog loaderDialog;
    private RetrofitApiCall apiClient;
    private View rootView;
    private static final String TAG = VerifyOTP.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        loaderDialog = new LoaderDialog();
        rootView = getWindow().getDecorView().getRootView();
        apiClient = DataController.getRetrofitInstance().create(RetrofitApiCall.class);
        setUpUi();
    }

    private void setUpUi(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        otpView = (OtpView) findViewById(R.id.otp_view);
        resendOtp = (Button) findViewById(R.id.resend_otp);
        submit = (Button) findViewById(R.id.submit);

        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.action_sign_up_short);
        myToolbar.setNavigationIcon(R.drawable.ic_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(this);
        resendOtp.setOnClickListener(this);

    }
    private void validation(){
        String otoStr=otpView.getOTP();

        if(TextUtils.isEmpty(otoStr)){
            otpView.requestFocus();
            SnakeAlert.setSnake(VerifyOTP.this,getString(R.string.error_field_required),rootView);
        }
        else {
            if(NetworkAvailability.chkStatus(VerifyOTP.this)){
                //callPrefferedCityApi();
            }
            else {
                SnakeAlert.setSnake(VerifyOTP.this,getString(R.string.no_network),rootView);
            }
        }

    }
    private void callverifyOTP(String mobile,String otp){
        Call<JsonObject> call = apiClient.verifyOTP(mobile, otp);
        loaderDialog.showDialog(VerifyOTP.this, false);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loaderDialog.dismissDialog(VerifyOTP.this);
                Log.e(TAG,"response :"+response);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG,"error :"+t.toString());
                loaderDialog.dismissDialog(VerifyOTP.this);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.resend_otp:
                break;

            case R.id.submit:
                Intent i = new Intent(VerifyOTP.this, ResetPassword.class);
                startActivity(i);
                break;

        }
    }
}
