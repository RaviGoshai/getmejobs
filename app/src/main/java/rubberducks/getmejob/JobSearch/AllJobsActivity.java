package rubberducks.getmejob.JobSearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import rubberducks.getmejob.R;
import rubberducks.getmejob.activity.Navigation;
import rubberducks.getmejob.activity.StudentHistory;
import rubberducks.getmejob.adapter.HomeAdapter;

public class AllJobsActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private RecyclerView allJobsRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_jobs);
        setUpUi();
        setUpAdapter();
    }

    private void setUpUi(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        allJobsRecycleView = (RecyclerView) findViewById(R.id.all_jobs_recycleView);
        allJobsRecycleView.setNestedScrollingEnabled(false);
        allJobsRecycleView.setHasFixedSize(false);

        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.job_search_header);
        myToolbar.setNavigationIcon(R.drawable.ic_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        myToolbar.inflateMenu(R.menu.job_serch);

        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.job_filter)
                {
                    Intent intent=new Intent(AllJobsActivity.this, PreferenceActivity.class);
                    startActivity(intent);
                }

                return false;
            }
        });
    }

    private void setUpAdapter(){
        ArrayList<String> list=new ArrayList<>();
        HomeAdapter homeAdapter=new HomeAdapter(AllJobsActivity.this,list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AllJobsActivity.this);
        allJobsRecycleView.setLayoutManager(mLayoutManager);
        allJobsRecycleView.setItemAnimator(new DefaultItemAnimator());
        allJobsRecycleView.setAdapter(homeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.job_serch, menu);
        return true;
    }

}
