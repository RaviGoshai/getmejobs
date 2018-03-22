package rubberducks.getmejob.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rubberducks.getmejob.Controller.DataController;
import rubberducks.getmejob.Data.Data;
import rubberducks.getmejob.Data.UserData;
import rubberducks.getmejob.Interface.RetrofitApiCall;
import rubberducks.getmejob.R;
import rubberducks.getmejob.Utils.LoaderDialog;
import rubberducks.getmejob.activity.HomeActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView forgetPass;
    private AutoCompleteTextView mobileNum;
    private EditText mPasswordView;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private LoaderDialog loaderDialog;
    private RetrofitApiCall apiClient;
    private Button emailSignInButton;
    private Toolbar myToolbar;
    private View rootView;
    String mobile, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loaderDialog = new LoaderDialog();
        rootView = getWindow().getDecorView().getRootView();
        apiClient = DataController.getRetrofitInstance().create(RetrofitApiCall.class);
        setUpUi();

    }

    private void setUpUi() {
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
        mobile = mobileNum.getText().toString();
        password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(mobile)) {
            mobileNum.setError(getString(R.string.error_field_required));
            focusView = mobileNum;
            cancel = true;

            if (cancel) {
                focusView.requestFocus();
            } else {

            }
        }
    }

    private void callLoginApi(String mobile, String password) {
        Call<UserData> call = apiClient.login(mobile, password);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {

                UserData userData = response.body();
                Data data = userData.getData();
                String name=data.getName();
                String email=data.getEmail();
                String mobile=data.getMobile();

                if (userData.getSuccess()) {

                    Intent home_intent = new Intent(LoginActivity.this, HomeActivity.class);
                    home_intent.putExtra("name", name);
                    home_intent.putExtra("email", email);
                    home_intent.putExtra("mobile", mobile);
                    startActivity(home_intent);
                } else {
                    Toast.makeText(LoginActivity.this, userData.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.e("Exception", t.getMessage());

            }
        });
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_pass:
                Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);
                break;

            case R.id.email_sign_in_button:
                loginValidation();
                callLoginApi(mobile, password);

                break;
        }
    }


}
