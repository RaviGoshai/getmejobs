package rubberducks.getmejob.Profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import rubberducks.getmejob.R;

public class QualificationActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private AutoCompleteTextView qualificationLevel;
    private AutoCompleteTextView course;
    private AutoCompleteTextView specialization;
    private AutoCompleteTextView universityInstitude;
    private AutoCompleteTextView passingOutYear;
    private Button qualificationSaveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualification);

        setUpUi();
    }
    private void setUpUi(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        qualificationLevel = (AutoCompleteTextView) findViewById(R.id.qualification_level);
        course = (AutoCompleteTextView) findViewById(R.id.course);
        specialization = (AutoCompleteTextView) findViewById(R.id.specialization);
        universityInstitude = (AutoCompleteTextView) findViewById(R.id.university_institude);
        passingOutYear = (AutoCompleteTextView) findViewById(R.id.passing_out_year);
        qualificationSaveBtn = (Button) findViewById(R.id.qualification_save_btn);

        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.qualification);
        myToolbar.setNavigationIcon(R.drawable.ic_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
