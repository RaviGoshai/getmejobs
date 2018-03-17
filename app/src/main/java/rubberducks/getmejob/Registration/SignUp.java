package rubberducks.getmejob.Registration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


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

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private LoaderDialog loaderDialog;
    private RetrofitApiCall apiClient;
    private View rootView;
    private static final String TAG = SignUp.class.getSimpleName();
    private Toolbar myToolbar;
    private AutoCompleteTextView name;
    private AutoCompleteTextView mobile;
    private AutoCompleteTextView email;
    private EditText password;

    private Button nextSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loaderDialog = new LoaderDialog();
        rootView = getWindow().getDecorView().getRootView();
        apiClient = DataController.getRetrofitInstance().create(RetrofitApiCall.class);
        if(NetworkAvailability.chkStatus(SignUp.this)){
            //callPrefferedCityApi();
        }
        else {
            SnakeAlert.setSnake(SignUp.this,getString(R.string.no_network),rootView);
        }
        setUpUI();


    }
    private void setUpUI(){
        name = (AutoCompleteTextView) findViewById(R.id.name);
        mobile = (AutoCompleteTextView) findViewById(R.id.mobile);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        nextSignUpButton = (Button) findViewById(R.id.next_sign_up_button);
        nextSignUpButton.setOnClickListener(this);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
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



    private void signUpValidation(){
        name.setError(null);
        mobile.setError(null);
        email.setError(null);
        password.setError(null);


        // Store values at the time of the login attempt.
        String nameStr = name.getText().toString();
        String mobileStr = mobile.getText().toString();
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();



        if(TextUtils.isEmpty(nameStr)){
            name.setError(getString(R.string.error_field_required));
            name.requestFocus();
        }
       else if(TextUtils.isEmpty(mobileStr)){
            mobile.setError(getString(R.string.error_field_required));
            mobile.requestFocus();
        }

        else if(mobileStr.length()<10){
            mobile.setError(getString(R.string.error_incorrect_mobile));
            mobile.requestFocus();
        }

        else if(TextUtils.isEmpty(emailStr)){
            email.setError(getString(R.string.error_field_required));
            email.requestFocus();
        }
        else if (!isEmailValid(emailStr)) {
            email.setError(getString(R.string.error_invalid_email));
            email.requestFocus();
        }
        else if(TextUtils.isEmpty(passwordStr)){
            password.setError(getString(R.string.error_field_required));
            password.requestFocus();
        }

        else if (!isPasswordValid(passwordStr)){
            password.setError(getString(R.string.error_invalid_password));
            password.requestFocus();
        }

        else {
            if(NetworkAvailability.chkStatus(SignUp.this)){
                //callPrefferedCityApi();
            }
            else {
                SnakeAlert.setSnake(SignUp.this,getString(R.string.no_network),rootView);
            }
        }

     //   AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.edit);

    }




    private void callPrefferedCityApi(){
        Call<JsonObject> call = apiClient.getPreferredLocation();
        loaderDialog.showDialog(SignUp.this, false);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loaderDialog.dismissDialog(SignUp.this);
                Log.e(TAG,"response :"+response);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loaderDialog.dismissDialog(SignUp.this);
                Log.e(TAG,"error :"+t.toString());
            }
        });
    }

/*    private void callSignUpApi(String name,String mobile,String email,String password,String token){
        Call<JsonObject> call = apiClient.userRegister(name,mobile,email,password,token);
        loaderDialog.showDialog(SignUp.this, false);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loaderDialog.dismissDialog(SignUp.this);
                Log.e(TAG,"response :"+response);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loaderDialog.dismissDialog(SignUp.this);
                Log.e(TAG,"error :"+t.toString());
            }
        });
    }*/

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
        switch (v.getId()){
            case R.id.next_sign_up_button:
                Intent i = new Intent(SignUp.this, VerifyOTP.class);
                startActivity(i);
                break;
        }
    }
}
