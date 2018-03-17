package rubberducks.getmejob.JobSearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import rubberducks.getmejob.R;

public class PreferenceActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private AutoCompleteTextView preferredLocation;
    private AutoCompleteTextView designation;
    private AutoCompleteTextView workExprience;
    private Button preferredLocationBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        setUpUi();
    }
    private void setUpUi(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        preferredLocation = (AutoCompleteTextView) findViewById(R.id.preferred_location);
        designation = (AutoCompleteTextView) findViewById(R.id.designation);
        workExprience = (AutoCompleteTextView) findViewById(R.id.work_exprience);
        preferredLocationBtn = (Button) findViewById(R.id.preferred_location_btn);

        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.preferences);
        myToolbar.setNavigationIcon(R.drawable.ic_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
