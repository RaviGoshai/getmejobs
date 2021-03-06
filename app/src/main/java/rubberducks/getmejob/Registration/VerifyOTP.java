package rubberducks.getmejob.Registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

public class VerifyOTP extends AppCompatActivity implements View.OnClickListener {
    private Toolbar myToolbar;
    private OtpView otpView;
    private Button resendOtp;
    private Button submit;
    private LoaderDialog loaderDialog;
    private RetrofitApiCall apiClient;
    private View rootView;
    private SharedPreferences sharedPreferences;
    private String nameStr, passwordStr, emailStr, contactStr, otpStr;
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

    private void setUpUi() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        otpView = (OtpView) findViewById(R.id.otp_view);
        resendOtp = (Button) findViewById(R.id.resend_otp);
        submit = (Button) findViewById(R.id.submit);

        sharedPreferences=getSharedPreferences("preference", Context.MODE_PRIVATE);
        emailStr= sharedPreferences.getString("email", "");
        nameStr= sharedPreferences.getString("name", "");
        contactStr= sharedPreferences.getString("mobile", "");
        passwordStr= sharedPreferences.getString("password", "");
        otpStr= otpView.getOTP();

        Toast.makeText(getApplicationContext(), emailStr+" "+nameStr+" "+contactStr+" "+passwordStr, Toast.LENGTH_LONG).show();

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
            SnakeAlert.setSnake(VerifyOTP.this, getString(R.string.error_field_required), rootView);
        } else {
            if (NetworkAvailability.chkStatus(VerifyOTP.this)) {
                //callPrefferedCityApi();
            } else {
                SnakeAlert.setSnake(VerifyOTP.this, getString(R.string.no_network), rootView);
            }
        }

    }

    private void callverifyOTP(String email, final String otp, String name, final String password, final String contact) {
        Call<JsonObject> call = apiClient.verifyOTP(email, otp, name, password, contact);
        loaderDialog.showDialog(VerifyOTP.this, false);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loaderDialog.dismissDialog(VerifyOTP.this);
                Log.e(TAG, "response :" + response);
                boolean isSussess = response.body().get("success").getAsBoolean();
                String message = response.body().get("message").getAsString();
                if (isSussess) {

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(VerifyOTP.this, ResetPassword.class);

                    i.putExtra("mobile", contact);
                    i.putExtra("password", password);
                    i.putExtra("otp", otp);
                    startActivity(i);
                } else {
                    Toast.makeText(VerifyOTP.this, "Please Enter correct OTP again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "error :" + t.toString());
                loaderDialog.dismissDialog(VerifyOTP.this);
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
                callverifyOTP(emailStr, otpStr, nameStr, passwordStr, contactStr);
                break;

        }
    }
}
