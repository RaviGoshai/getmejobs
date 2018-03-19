package rubberducks.getmejob.JobSearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import rubberducks.getmejob.R;
import rubberducks.getmejob.adapter.HomeAdapter;
import rubberducks.getmejob.adapter.JobStatusAdapter;

public class JobStatusActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private RecyclerView jobStatusRecycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_status);

        setUpUi();
        setUpAdapter();
    }

    private void setUpUi(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        jobStatusRecycle = (RecyclerView) findViewById(R.id.job_status_recycle);

        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.job_status);
        myToolbar.setNavigationIcon(R.drawable.ic_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setUpAdapter(){
        ArrayList<String> list=new ArrayList<>();
        JobStatusAdapter jobStatusAdapter=new JobStatusAdapter(JobStatusActivity.this,list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(JobStatusActivity.this);
        jobStatusRecycle.setLayoutManager(mLayoutManager);
        jobStatusRecycle.setItemAnimator(new DefaultItemAnimator());
        jobStatusRecycle.setAdapter(jobStatusAdapter);
    }
}
