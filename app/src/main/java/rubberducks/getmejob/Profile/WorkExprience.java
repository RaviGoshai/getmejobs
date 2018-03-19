package rubberducks.getmejob.Profile;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rubberducks.getmejob.R;

public class WorkExprience extends AppCompatActivity implements View.OnClickListener{
    private Toolbar myToolbar;
    private AutoCompleteTextView currentDesignation;
    private AutoCompleteTextView currentCompany;
    private AutoCompleteTextView currentSalary;
    private AutoCompleteTextView joiningDate;
    private AutoCompleteTextView leavingDate;
    private AutoCompleteTextView noticePeriod;
    private Button workExprienceSaveBtn;
    private int date_flag=0;
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

        applyListner();
    }
    private void  applyListner(){
        leavingDate.setOnClickListener(this);
        joiningDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.leaving_date:
                date_flag=0;
                showDatePicker();
                break;

            case R.id.joining_date:
                date_flag=1;
                showDatePicker();
                break;
        }
    }


    private void showDatePicker() {
        Log.e("flaggg",""+date_flag);
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                if (date_flag==1){
                    joiningDate.setText(dateFormatter.format(newDate.getTime()));
                }
                else {
                    leavingDate.setText(dateFormatter.format(newDate.getTime()));
                }

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
