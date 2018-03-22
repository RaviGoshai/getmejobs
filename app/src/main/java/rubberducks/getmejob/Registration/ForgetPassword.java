package rubberducks.getmejob.Registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rubberducks.getmejob.Controller.DataController;
import rubberducks.getmejob.Interface.RetrofitApiCall;
import rubberducks.getmejob.R;
import rubberducks.getmejob.Utils.LoaderDialog;
import rubberducks.getmejob.Utils.NetworkAvailability;
import rubberducks.getmejob.Utils.SnakeAlert;

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener {

    private Toolbar myToolbar;
    private AutoCompleteTextView mobile;
    private Button nextForgetPassButton;
    private LoaderDialog loaderDialog;
    private RetrofitApiCall apiClient;
    private View rootView;
    private String mobileStr;
    private static final String TAG = ForgetPassword.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        loaderDialog = new LoaderDialog();
        rootView = getWindow().getDecorView().getRootView();
        apiClient = DataController.getRetrofitInstance().create(RetrofitApiCall.class);
        setUpUi();
    }
    private void setUpUi(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mobile = (AutoCompleteTextView) findViewById(R.id.mobile);
        nextForgetPassButton = (Button) findViewById(R.id.next_forget_pass_button);
        nextForgetPassButton.setOnClickListener(this);

        myToolbar.setTitle(R.string.forget_passsword);
        myToolbar.setNavigationIcon(R.drawable.ic_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_forget_pass_button:
                validation();
                callForgetPassApi(mobileStr);
                break;
        }
    }

    private void validation(){
       mobileStr = mobile.getText().toString();

        if(TextUtils.isEmpty(mobileStr)){
            mobile.setError(getString(R.string.error_field_required));
            mobile.requestFocus();
        }
        else {
            if(NetworkAvailability.chkStatus(ForgetPassword.this)){
                //callPrefferedCityApi();
            }
            else {
                SnakeAlert.setSnake(ForgetPassword.this,getString(R.string.no_network),rootView);
            }
        }
    }

    private void callForgetPassApi(String mobile){
        Call<JsonObject> call = apiClient.forgetPassword(mobile);
        loaderDialog.showDialog(ForgetPassword.this, false);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loaderDialog.dismissDialog(ForgetPassword.this);
                Log.e(TAG,"response :"+response);

                boolean isSuccess= response.body().get("success").getAsBoolean();
                String message= response.body().get("message").getAsString();
                if(isSuccess){
                    Intent i = new Intent(ForgetPassword.this, VerifyForgetOtp.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG,"error :"+t.toString());
                loaderDialog.dismissDialog(ForgetPassword.this);
            }
        });
    }
}
