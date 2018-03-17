package rubberducks.getmejob.Registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class ResetPassword extends AppCompatActivity  implements View.OnClickListener{
    private Toolbar myToolbar;
    private EditText confirmPassword;
    private EditText reEnterPassword;
    private Button save;
    private LoaderDialog loaderDialog;
    private RetrofitApiCall apiClient;
    private View rootView;
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

    private void setUpUi(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        reEnterPassword = (EditText) findViewById(R.id.re_enter_password);
        save = (Button) findViewById(R.id.save);

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
    private void validation(){
        String passwordStr = confirmPassword.getText().toString();
        String reEnterpasswordStr = reEnterPassword.getText().toString();

        if(TextUtils.isEmpty(passwordStr)){
            confirmPassword.setError(getString(R.string.error_field_required));
            confirmPassword.requestFocus();
        }
        else  if(TextUtils.isEmpty(reEnterpasswordStr)){
            reEnterPassword.setError(getString(R.string.error_field_required));
            reEnterPassword.requestFocus();
        }
        else if(!passwordStr.equals(reEnterpasswordStr)){
            SnakeAlert.setSnake(ResetPassword.this,getString(R.string.error_field_password),rootView);
        }
        else {
            if(NetworkAvailability.chkStatus(ResetPassword.this)){
                //callPrefferedCityApi();
            }
            else {
            SnakeAlert.setSnake(ResetPassword.this,getString(R.string.no_network),rootView);
        }
        }

    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                Intent i = new Intent(ResetPassword.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
    private void callResetPassApi(String seekerId,String mobile){
        Call<JsonObject> call = apiClient.resetPassword(seekerId,mobile);
        loaderDialog.showDialog(ResetPassword.this, false);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loaderDialog.dismissDialog(ResetPassword.this);
                Log.e(TAG,"response :"+response);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG,"error :"+t.toString());
                loaderDialog.dismissDialog(ResetPassword.this);
            }
        });
    }
}
