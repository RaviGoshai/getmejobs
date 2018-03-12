package rubberducks.getmejob.Registration;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rubberducks.getmejob.R;
import rubberducks.getmejob.Utils.PreferenceConnector;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

          new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginSelection.class);
                startActivity(i);
                finish();

            }
        }, 2 * 1000);
    }

}
