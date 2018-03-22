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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rubberducks.getmejob.Controller.DataController;
import rubberducks.getmejob.Data.RegisterValidate;
import rubberducks.getmejob.Interface.RetrofitApiCall;
import rubberducks.getmejob.R;
import rubberducks.getmejob.Utils.LoaderDialog;
import rubberducks.getmejob.Utils.NetworkAvailability;
import rubberducks.getmejob.Utils.SnakeAlert;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private LoaderDialog loaderDialog;
    private RetrofitApiCall apiClient;
    private View rootView;
    public static final String MESSAGE = "Email already exist!";
    private static final String TAG = SignUp.class.getSimpleName();
    private Toolbar myToolbar;
    private AutoCompleteTextView name;
    private AutoCompleteTextView mobile;
    private AutoCompleteTextView email;
    private EditText password;
    private TextView loginHere;
    private String nameStr;
    private String mobileStr;
    private String emailStr;
    private String passwordStr;
    private SharedPreferences sharedPreferences;


    private Button nextSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // loaderDialog = new LoaderDialog();
        rootView = getWindow().getDecorView().getRootView();
        apiClient = DataController.getRetrofitInstance().create(RetrofitApiCall.class);
        if (NetworkAvailability.chkStatus(SignUp.this)) {
            //callPrefferedCityApi();
        } else {
            SnakeAlert.setSnake(SignUp.this, getString(R.string.no_network), rootView);
        }
        setUpUI();

        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(in);
            }
        });


    }


    private void setUpUI() {
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        nextSignUpButton = findViewById(R.id.next_sign_up_button);
        loginHere=findViewById(R.id.login_here);
        nextSignUpButton.setOnClickListener(this);
        sharedPreferences=getSharedPreferences("preference", Context.MODE_PRIVATE);


        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.action_sign_up_short);
        myToolbar.setNavigationIcon(R.drawable.ic_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }



    private void signUpValidation() {
        name.setError(null);
        mobile.setError(null);
        email.setError(null);
        password.setError(null);


        // Store values at the time of the login attempt.
        nameStr = name.getText().toString();
        mobileStr = mobile.getText().toString();
        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();


        if (TextUtils.isEmpty(nameStr)) {
            name.setError(getString(R.string.error_field_required));
            name.requestFocus();
        } else if (TextUtils.isEmpty(mobileStr)) {
            mobile.setError(getString(R.string.error_field_required));
            mobile.requestFocus();
        } else if (mobileStr.length()!= 10) {
            mobile.setError(getString(R.string.error_incorrect_mobile));
            mobile.requestFocus();
        } else if (TextUtils.isEmpty(emailStr)) {
            email.setError(getString(R.string.error_field_required));
            email.requestFocus();
        } else if (!isEmailValid(emailStr)) {
            email.setError(getString(R.string.error_invalid_email));
            email.requestFocus();
        } else if (TextUtils.isEmpty(passwordStr)) {
            password.setError(getString(R.string.error_field_required));
            password.requestFocus();
        } else if (!isPasswordValid(passwordStr)) {
            password.setError(getString(R.string.error_invalid_password));
            password.requestFocus();
        } else {
            if (NetworkAvailability.chkStatus(SignUp.this)) {
                //callPrefferedCityApi();
            } else {
                SnakeAlert.setSnake(SignUp.this, getString(R.string.no_network), rootView);
            }
        }

        //   AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.edit);

    }


    private void callPrefferedCityApi() {
        Call<JsonObject> call = apiClient.getPreferredLocation();
        //loaderDialog.showDialog(SignUp.this, false);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                // loaderDialog.dismissDialog(SignUp.this);
                Log.e(TAG, "response :" + response);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // loaderDialog.dismissDialog(SignUp.this);
                Log.e(TAG, "error :" + t.toString());
            }
        });
    }

    private void callSignUpApi(String name, final String mobile, final String email, String password) {
        Call<JsonObject> call = apiClient.userRegister(mobile, email);

        //loaderDialog.showDialog(SignUp.this, false);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject validate = response.body();
                boolean b = validate.get("success").getAsBoolean();
                String message = String.valueOf(validate.get("message").getAsString());
                if (b) {
                    Intent in = new Intent(SignUp.this, VerifyOTP.class);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("email", emailStr);
                    editor.putString("name", nameStr);
                    editor.putString("mobile", mobileStr);
                    editor.putString("password", passwordStr);
                    editor.commit();
                    startActivity(in);

                } else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Error : " + call, Toast.LENGTH_LONG).show();
                Log.e("Excecption", "" + t.getMessage());
            }
        });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.next_sign_up_button:
                signUpValidation();
                callSignUpApi(nameStr, mobileStr, emailStr, passwordStr);



                break;
        }
    }
}
