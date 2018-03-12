package rubberducks.getmejob.Registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mukesh.OtpView;

import rubberducks.getmejob.R;

public class VerifyOTP extends AppCompatActivity implements View.OnClickListener{
    private Toolbar myToolbar;
    private ImageView backBtn;
    private OtpView otpView;
    private Button resendOtp;
    private Button editNumber;
    private Button nextVerifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        setUpUi();
    }

    private void setUpUi(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        backBtn = (ImageView) findViewById(R.id.back_btn);
        otpView = (OtpView) findViewById(R.id.otp_view);
        resendOtp = (Button) findViewById(R.id.resend_otp);
        editNumber = (Button) findViewById(R.id.edit_number);
        nextVerifyButton = (Button) findViewById(R.id.next_verify_button);


        editNumber.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        resendOtp.setOnClickListener(this);
        nextVerifyButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                onBackPressed();
                break;

            case R.id.resend_otp:
                break;

            case R.id.next_verify_button:
                Intent i = new Intent(VerifyOTP.this, ResetPassword.class);
                startActivity(i);
                break;

            case R.id.edit_number:
                onBackPressed();
                break;
        }
    }
}
