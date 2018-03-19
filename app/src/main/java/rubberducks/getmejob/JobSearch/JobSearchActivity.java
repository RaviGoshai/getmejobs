package rubberducks.getmejob.JobSearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import rubberducks.getmejob.R;

public class JobSearchActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar myToolbar;
    private AutoCompleteTextView skill;
    private AutoCompleteTextView location;
    private AutoCompleteTextView workExprience;
    private AutoCompleteTextView minimumSalary;
    private Button jobSearchBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);

        setUpUi();
    }
    private void setUpUi(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        skill = (AutoCompleteTextView) findViewById(R.id.skill);
        location = (AutoCompleteTextView) findViewById(R.id.location);
        workExprience = (AutoCompleteTextView) findViewById(R.id.work_exprience);
        minimumSalary = (AutoCompleteTextView) findViewById(R.id.minimum_salary);
        jobSearchBtn = (Button) findViewById(R.id.job_search_btn);
        jobSearchBtn.setOnClickListener(this);

        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.job_search_header);
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
            case R.id.job_search_btn:
                Intent intent=new Intent(JobSearchActivity.this,AllJobsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
