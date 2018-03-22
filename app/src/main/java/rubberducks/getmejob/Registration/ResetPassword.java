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
import android.widget.EditText;
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

public class ResetPassword extends AppCompatActivity implements View.OnClickListener {
    private Toolbar myToolbar;
    private EditText confirmPassword;
    private EditText reEnterPassword;
    private Button save;
    private LoaderDialog loaderDialog;
    private RetrofitApiCall apiClient;
    private SharedPreferences sharedPreferences;
    private View rootView;
    String  mobileStr , passwordStr, reEnterpasswordStr;

    private static final String TAG = ResetPassword.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        loaderDialog = new LoaderDialog();
        rootView = getWindow().getDecorView().getRootView();
        apiClient = DataController.getRetrofitInstance().create(RetrofitApiCall.class);
        setUpUi();
    }

    private void setUpUi() {
        myToolbar =  findViewById(R.id.my_toolbar);
        confirmPassword =  findViewById(R.id.confirm_password);
        reEnterPassword =  findViewById(R.id.re_enter_password);
        save =  findViewById(R.id.save);
        save.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("preference",
                Context.MODE_PRIVATE);
        mobileStr=sharedPreferences.getString("mobile", "");
        Toast.makeText(getApplicationContext(), "Shared : "+mobileStr, Toast.LENGTH_LONG).show();

        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.reset_password);
        myToolbar.setNavigationIcon(R.drawable.ic_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void validation() {
        String passwordStr = confirmPassword.getText().toString();
        String reEnterpasswordStr = reEnterPassword.getText().toString();

        if (TextUtils.isEmpty(passwordStr)) {
            confirmPassword.setError(getString(R.string.error_field_required));
            confirmPassword.requestFocus();
        } else if (TextUtils.isEmpty(reEnterpasswordStr)) {
            reEnterPassword.setError(getString(R.string.error_field_required));
            reEnterPassword.requestFocus();
        } else if (!passwordStr.equals(reEnterpasswordStr)) {
            SnakeAlert.setSnake(ResetPassword.this, getString(R.string.error_field_password), rootView);
        } else {
            if (NetworkAvailability.chkStatus(ResetPassword.this)) {
                //callPrefferedCityApi();
            } else {
                SnakeAlert.setSnake(ResetPassword.this, getString(R.string.no_network), rootView);
            }
        }

    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    private void callResetPassApi(String mobile, String password) {
        Call<JsonObject> call = apiClient.resetPassword( mobile, password);
        loaderDialog.showDialog(ResetPassword.this, false);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loaderDialog.dismissDialog(ResetPassword.this);
                Log.e(TAG, "response :" + response);
                boolean b = response.body().get("success").getAsBoolean();
                String message = response.body().get("message").getAsString();
                if (b) {
                    Intent i = new Intent(ResetPassword.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "error :" + t.toString());
                loaderDialog.dismissDialog(ResetPassword.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_LONG).show();
                if(passwordStr.equals(reEnterpasswordStr)) {
                    validation();
                    setUpUi();
                    callResetPassApi(mobileStr, reEnterpasswordStr);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Password Incorrect!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
