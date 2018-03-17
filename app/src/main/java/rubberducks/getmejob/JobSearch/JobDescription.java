package rubberducks.getmejob.JobSearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import rubberducks.getmejob.R;
import rubberducks.getmejob.adapter.HomeAdapter;

public class JobDescription extends AppCompatActivity  implements View.OnClickListener{
    private Toolbar myToolbar;
    private Button jobSearchBtn;
    private RecyclerView jobDesRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_description);
        setUpUi();
        setUpAdapter();
    }
       private void setUpUi(){

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        jobSearchBtn = (Button) findViewById(R.id.job_search_btn);
        jobDesRecycleView = (RecyclerView) findViewById(R.id.job_des_recycleView);
           jobDesRecycleView.setNestedScrollingEnabled(false);
           jobDesRecycleView.setHasFixedSize(false);


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

    }

    private void setUpAdapter(){
        ArrayList<String> list=new ArrayList<>();
        HomeAdapter homeAdapter=new HomeAdapter(JobDescription.this,list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(JobDescription.this);
        jobDesRecycleView.setLayoutManager(mLayoutManager);
        jobDesRecycleView.setItemAnimator(new DefaultItemAnimator());
        jobDesRecycleView.setAdapter(homeAdapter);

    }
}
