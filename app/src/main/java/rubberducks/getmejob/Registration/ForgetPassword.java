package rubberducks.getmejob.Registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import rubberducks.getmejob.R;

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener {

    private Toolbar myToolbar;
    private AutoCompleteTextView mobile;
    private Button nextForgetPassButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
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
                Intent i = new Intent(ForgetPassword.this, VerifyOTP.class);
                startActivity(i);
                break;
        }
    }
}
