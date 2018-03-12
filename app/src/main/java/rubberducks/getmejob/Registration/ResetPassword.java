package rubberducks.getmejob.Registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rubberducks.getmejob.R;

public class ResetPassword extends AppCompatActivity  implements View.OnClickListener{
    private Toolbar myToolbar;
    private EditText confirmPassword;
    private EditText reEnterPassword;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
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
}
