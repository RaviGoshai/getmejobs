package rubberducks.getmejob.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import rubberducks.getmejob.JobSearch.JobDescription;
import rubberducks.getmejob.R;
import rubberducks.getmejob.adapter.HomeAdapter;

public class HomeActivity extends Navigation  implements HomeAdapter.OnItemClickListener{
    private RecyclerView homeRecycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_home);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        frameLayout.addView(contentView, 0);
        setUpUi();
        setUpAdapter();
    }

    private void setUpUi(){
        homeRecycleview = (RecyclerView) findViewById(R.id.home_recycleview);
        homeRecycleview.setNestedScrollingEnabled(false);
        homeRecycleview.setHasFixedSize(false);

    }
    private void setUpAdapter(){
        ArrayList<String> list=new ArrayList<>();
        HomeAdapter homeAdapter=new HomeAdapter(HomeActivity.this,list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(HomeActivity.this);
        homeRecycleview.setLayoutManager(mLayoutManager);
        homeRecycleview.setItemAnimator(new DefaultItemAnimator());
        homeRecycleview.setAdapter(homeAdapter);
        homeAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent(HomeActivity.this, JobDescription.class);
        startActivity(intent);
    }
}
