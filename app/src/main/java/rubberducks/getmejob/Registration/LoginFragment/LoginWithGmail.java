package rubberducks.getmejob.Registration.LoginFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rubberducks.getmejob.Controller.DataController;
import rubberducks.getmejob.Interface.Constants;
import rubberducks.getmejob.Interface.RetrofitApiCall;
import rubberducks.getmejob.R;
import rubberducks.getmejob.Registration.ForgetPassword;
import rubberducks.getmejob.Registration.LoginActivity;
import rubberducks.getmejob.Utils.LoaderDialog;
import rubberducks.getmejob.activity.HomeActivity;
import rubberducks.getmejob.activity.Navigation;

/**
 * Created by ADMIN on 3/10/2018.
 */

public class LoginWithGmail extends Fragment implements View.OnClickListener {
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private LoaderDialog loaderDialog;
    private RetrofitApiCall apiClient;
    private TextView forgetPass;
    private Button emailSignInButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.login_with_gmail,container,false);
        apiClient = DataController.getRetrofitInstance().create(RetrofitApiCall.class);
        loaderDialog = new LoaderDialog();
        setUpUi(view);
        return view;
    }


    private void setUpUi(View view){
        mEmailView = (AutoCompleteTextView) view.findViewById(R.id.email);
        mPasswordView = (EditText)view. findViewById(R.id.password);
        forgetPass = (TextView)view. findViewById(R.id.forget_pass);
        emailSignInButton = (Button)view.findViewById(R.id.email_sign_in_button);
        emailSignInButton.setOnClickListener(this);
        forgetPass.setOnClickListener(this);
    }

    private void loginValidation() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
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
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

        }
    }
    private void callLoginApi(String email,String password){
        Call<Constants> call = apiClient.login(email, password);
        loaderDialog.showDialog(getActivity(), false);
        call.enqueue(new Callback<Constants>() {
            @Override
            public void onResponse(Call<Constants> call, Response<Constants> response) {
                loaderDialog.dismissDialog(getActivity());
                Log.e(TAG,"response :"+response);
            }

            @Override
            public void onFailure(Call<Constants> call, Throwable t) {
                Log.e(TAG,"error :"+t.toString());
                loaderDialog.dismissDialog(getActivity());
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
        switch (v.getId()){
            case R.id.forget_pass:
                Intent intent=new Intent(getActivity(), ForgetPassword.class);
                startActivity(intent);
                break;

            case R.id.email_sign_in_button:
                Intent home_intent=new Intent(getActivity(), HomeActivity.class);
                startActivity(home_intent);
                break;
        }
    }
}
