package rubberducks.getmejob.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import rubberducks.getmejob.JobSearch.AllJobsActivity;
import rubberducks.getmejob.R;
import rubberducks.getmejob.adapter.HomeAdapter;
import rubberducks.getmejob.adapter.NotificationAdapter;

public class Notifcation extends AppCompatActivity {
    private Toolbar myToolbar;
    private RecyclerView notificationRecycleview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifcation);
        setUpUi();
        setUpAdapter();
    }
    private void setUpUi(){
        notificationRecycleview = (RecyclerView) findViewById(R.id.notification_recycleview);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.notification);
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
        NotificationAdapter notificationAdapter=new NotificationAdapter(Notifcation.this,list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Notifcation.this);
        notificationRecycleview.setLayoutManager(mLayoutManager);
        notificationRecycleview.setItemAnimator(new DefaultItemAnimator());
        notificationRecycleview.setAdapter(notificationAdapter);

    }
}
