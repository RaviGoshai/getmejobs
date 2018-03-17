package rubberducks.getmejob.Profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import rubberducks.getmejob.R;

public class WorkExprience extends AppCompatActivity {
    private Toolbar myToolbar;
    private AutoCompleteTextView currentDesignation;
    private AutoCompleteTextView currentCompany;
    private AutoCompleteTextView currentSalary;
    private AutoCompleteTextView joiningDate;
    private AutoCompleteTextView leavingDate;
    private AutoCompleteTextView noticePeriod;
    private Button workExprienceSaveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_exprience);
        setUpUi();
    }
    private void setUpUi(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        currentDesignation = (AutoCompleteTextView) findViewById(R.id.current_designation);
        currentCompany = (AutoCompleteTextView) findViewById(R.id.current_company);
        currentSalary = (AutoCompleteTextView) findViewById(R.id.current_salary);
        joiningDate = (AutoCompleteTextView) findViewById(R.id.joining_date);
        leavingDate = (AutoCompleteTextView) findViewById(R.id.leaving_date);
        noticePeriod = (AutoCompleteTextView) findViewById(R.id.notice_period);
        workExprienceSaveBtn = (Button) findViewById(R.id.workExprience_save_btn);



        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.work_exprience);
        myToolbar.setNavigationIcon(R.drawable.ic_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
