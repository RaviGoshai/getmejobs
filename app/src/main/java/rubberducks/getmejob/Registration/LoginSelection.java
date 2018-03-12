package rubberducks.getmejob.Registration;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import rubberducks.getmejob.Interface.Constants;
import rubberducks.getmejob.R;

public class LoginSelection extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{
    private CallbackManager callbackManager;
    private LoginButton loginButtonFB;
    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = LoginSelection.class.getSimpleName();
    private LinearLayout register;
    private LinearLayout login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setApplicationId(Constants.FACEBOOK_APP_ID);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.candidate_selection);

        setUpUi();
    }

    private void setUpUi(){
        register = (LinearLayout) findViewById(R.id.register);
        login = (LinearLayout) findViewById(R.id.login);
        register.setOnClickListener(this);
        login.setOnClickListener(this);

    }
    private void openLinkedIn(){
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                //  Toast.makeText(MainActivity.this, ""+LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken().getValue(), Toast.LENGTH_SHORT).show();
                // Authentication was successful.  You can now do
                // other calls with the SDK.
            }

            @Override
            public void onAuthError(LIAuthError error) {
                // Handle authentication errors
            }
        }, true);
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE,Scope.R_EMAILADDRESS);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void setUpGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(Constants.GOOGAL_AUTH_KEY)
                .requestEmail()//
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, LoginSelection.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void getFbData() {
        loginButtonFB.performClick();
        loginButtonFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                System.out.println("onSuccess");

                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());

                        String facebook_id=null,email = null,first_name=null,last_name=null;
                        try {
                            if (object.has("id")){
                                facebook_id= object.getString("id");
                            }
                            if (object.has("email")){
                                email=object.getString("email");
                            }
                            else {
                                email = first_name+last_name + "@gmail.com";
                            }
                            if (object.has("first_name")){
                                first_name=object.getString("first_name");
                            }
                            if (object.has("last_name")){
                                last_name=object.getString("last_name");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");

            }
        });
    }

   private String getHasKey() {
        String key = null;
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "rubberducks.getmejob",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                key = Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {


        }
        return key;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                Intent intent = new Intent(LoginSelection.this,SignUp.class);
                startActivity(intent);
                break;

            case R.id.login:
                Intent login_intent = new Intent(LoginSelection.this,LoginActivity.class);
                startActivity(login_intent);
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {

        try {
            Log.e(TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                Log.e(TAG, "display name: " + acct.getDisplayName());
                String personName = acct.getDisplayName();
                //  String personPhotoUrl = acct.getPhotoUrl().toString();
                String email = acct.getEmail();
                String email_token = acct.getId();

            } else {
                // Signed out, show unauthenticated UI.
                // updateUI(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ssssssssss", "" + e);
        }
    }

    private void callLinkedInApi(){
        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,public-profile-url,picture-url,email-address,picture-urls::(original))";

        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(this, url.trim(), new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                Log.e("response",""+apiResponse);
                try {

                    JSONObject jsonObject=apiResponse.getResponseDataAsJson();
                    String emailAddress=jsonObject.getString("emailAddress");
                    String firstName=jsonObject.getString("firstName");
                    String id=jsonObject.getString("id");
                   // Toast.makeText(LoginSelection.this, ""+emailAddress+firstName+id, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onApiError(LIApiError liApiError) {
                Log.e("error",liApiError.toString());

            }
        });
    }
}
