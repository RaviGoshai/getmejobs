package rubberducks.getmejob.Registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mukesh.OtpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rubberducks.getmejob.Controller.DataController;
import rubberducks.getmejob.Interface.RetrofitApiCall;
import rubberducks.getmejob.R;
import rubberducks.getmejob.Utils.LoaderDialog;
import rubberducks.getmejob.Utils.NetworkAvailability;
import rubberducks.getmejob.Utils.SnakeAlert;

public class VerifyForgetOtp extends AppCompatActivity implements View.OnClickListener {
    private Toolbar myToolbar;
    private OtpView otpView;
    private Button resendOtp;
    private Button submit;
    private LoaderDialog loaderDialog;
    private RetrofitApiCall apiClient;
    private View rootView;
    private SharedPreferences sharedPreferences;
    private String otpStr;
    private static final String TAG = VerifyOTP.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_forget_otp);

        loaderDialog = new LoaderDialog();
        rootView = getWindow().getDecorView().getRootView();
        apiClient = DataController.getRetrofitInstance().create(RetrofitApiCall.class);
        setUpUi();
    }

    private void setUpUi() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        otpView = (OtpView) findViewById(R.id.otp_view);
        resendOtp = (Button) findViewById(R.id.resend_otp);
        submit = (Button) findViewById(R.id.submit);
        otpStr= otpView.getOTP();


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

    private void validation() {
        String otoStr = otpView.getOTP();

        if (TextUtils.isEmpty(otoStr)) {
            otpView.requestFocus();
            SnakeAlert.setSnake(VerifyForgetOtp.this, getString(R.string.error_field_required), rootView);
        } else {
            if (NetworkAvailability.chkStatus(VerifyForgetOtp.this)) {
                //callPrefferedCityApi();
            } else {
                SnakeAlert.setSnake(VerifyForgetOtp.this, getString(R.string.no_network), rootView);
            }
        }

    }

    private void callforgetverifyOtp(final String otp) {
        Call<JsonObject> call = apiClient.forgetPasswordVarifyOtp(otp);
        loaderDialog.showDialog(VerifyForgetOtp.this, false);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loaderDialog.dismissDialog(VerifyForgetOtp.this);
                boolean isSuccess = response.body().get("success").getAsBoolean();
                String message = response.body().get("message").getAsString();
                if (isSuccess) {

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(VerifyForgetOtp.this, ResetPassword.class);
                    startActivity(i);
                } else {
                    Toast.makeText(VerifyForgetOtp.this, "Please Enter correct OTP again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "error :" + t.toString());
                loaderDialog.dismissDialog(VerifyForgetOtp.this);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.resend_otp:
                break;

            case R.id.submit:
                setUpUi();
                callforgetverifyOtp(otpStr);
                break;

        }
    }
}
