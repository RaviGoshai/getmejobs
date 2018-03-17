package rubberducks.getmejob.Registration;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rubberducks.getmejob.Controller.DataController;
import rubberducks.getmejob.Interface.Constants;
import rubberducks.getmejob.Interface.RetrofitApiCall;
import rubberducks.getmejob.R;
import rubberducks.getmejob.Registration.LoginFragment.Pager;
import rubberducks.getmejob.Utils.LoaderDialog;
import rubberducks.getmejob.activity.HomeActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView forgetPass;
    private AutoCompleteTextView mobileNum;
    private EditText mPasswordView;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private LoaderDialog loaderDialog;
    private RetrofitApiCall apiClient;
    private Button emailSignInButton;;
    private Toolbar myToolbar;
    private View rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loaderDialog = new LoaderDialog();
        rootView = getWindow().getDecorView().getRootView();
        apiClient = DataController.getRetrofitInstance().create(RetrofitApiCall.class);
        setUpUi();

    }

    private void setUpUi(){
        mobileNum = (AutoCompleteTextView) findViewById(R.id.mobile);
        mPasswordView = (EditText) findViewById(R.id.password);
        forgetPass = (TextView) findViewById(R.id.forget_pass);
        emailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.login);
        myToolbar.setNavigationIcon(R.drawable.ic_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        emailSignInButton.setOnClickListener(this);
        forgetPass.setOnClickListener(this);
    }
    private void loginValidation() {
        // Reset errors.
        mobileNum.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mobileNum.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mobileNum.setError(getString(R.string.error_field_required));
            focusView = mobileNum;
            cancel = true;

            if (cancel) {
                focusView.requestFocus();
            } else {

            }
        }}
    private void callLoginApi(String email,String password){
        Call<Constants> call = apiClient.login(email, password);
        loaderDialog.showDialog(LoginActivity.this, false);
        call.enqueue(new Callback<Constants>() {
            @Override
            public void onResponse(Call<Constants> call, Response<Constants> response) {
                loaderDialog.dismissDialog(LoginActivity.this);
                Log.e(TAG,"response :"+response);
            }

            @Override
            public void onFailure(Call<Constants> call, Throwable t) {
                Log.e(TAG,"error :"+t.toString());
                loaderDialog.dismissDialog(LoginActivity.this);
            }
        });
    }



    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forget_pass:
                Intent intent=new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);
                break;

            case R.id.email_sign_in_button:
                Intent home_intent=new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(home_intent);
                break;
        }
    }



}
