package rubberducks.getmejob.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import rubberducks.getmejob.JobSearch.JobDescription;
import rubberducks.getmejob.R;
import rubberducks.getmejob.adapter.HomeAdapter;

public class HomeActivity extends Navigation implements HomeAdapter.OnItemClickListener {
    private RecyclerView homeRecycleview;
    private TextView textName;
    private TextView textEmail;
    private TextView textMobile;
    private TextView textAppliedJobs;
    private TextView textSavedJobs;
    private ImageView imageProfile;


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

    private void setUpUi() {
        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        textMobile = findViewById(R.id.textMobile);
        textAppliedJobs = findViewById(R.id.textAppliedJobs);
        textSavedJobs = findViewById(R.id.textSavedJobs);
        imageProfile = findViewById(R.id.imageProfile);
        homeRecycleview = (RecyclerView) findViewById(R.id.home_recycleview);
        homeRecycleview.setNestedScrollingEnabled(false);
        homeRecycleview.setHasFixedSize(false);

        Intent getData = getIntent();
        String name = getData.getStringExtra("name");
        String email = getData.getStringExtra("email");
        String mobile = getData.getStringExtra("mobile");

        textMobile.setText(mobile);
        textName.setText(name);
        textEmail.setText(email);

    }

    private void setUpAdapter() {
        ArrayList<String> list = new ArrayList<>();
        HomeAdapter homeAdapter = new HomeAdapter(HomeActivity.this, list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(HomeActivity.this);
        homeRecycleview.setLayoutManager(mLayoutManager);
        homeRecycleview.setItemAnimator(new DefaultItemAnimator());
        homeRecycleview.setAdapter(homeAdapter);
        homeAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(HomeActivity.this, JobDescription.class);
        startActivity(intent);
    }


}

