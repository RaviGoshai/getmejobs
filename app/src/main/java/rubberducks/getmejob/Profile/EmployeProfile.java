package rubberducks.getmejob.Profile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rubberducks.getmejob.R;

public class EmployeProfile extends AppCompatActivity  implements View.OnClickListener{
    private Toolbar myToolbar;
    private Button jobSearchBtn;
    private TextView qualificationTxt;
    private TextView workExprienceTxt;
    private AutoCompleteTextView mobile;
    private AutoCompleteTextView dob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employe_profile);
        setUpUi();
    }

    private void setUpUi(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        jobSearchBtn = (Button) findViewById(R.id.job_search_btn);
        qualificationTxt = (TextView) findViewById(R.id.qualification_txt);
        workExprienceTxt = (TextView) findViewById(R.id.work_exprience_txt);
        mobile = (AutoCompleteTextView) findViewById(R.id.mobile);
        dob = (AutoCompleteTextView) findViewById(R.id.dob);

        qualificationTxt.setOnClickListener(this);
        workExprienceTxt.setOnClickListener(this);
        dob.setOnClickListener(this);

        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.profile);
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
            case R.id.qualification_txt:
                Intent intent=new Intent(EmployeProfile.this,QualificationActivity.class);
                startActivity(intent);
                break;

            case R.id.work_exprience_txt:
                Intent work_intent=new Intent(EmployeProfile.this,WorkExprience.class);
                startActivity(work_intent);
                break;

            case R.id.dob:
                showDatePicker();
                break;
        }
    }

    private void showDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                dob.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
