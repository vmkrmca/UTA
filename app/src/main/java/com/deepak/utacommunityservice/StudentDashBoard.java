package com.deepak.utacommunityservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentDashBoard extends Activity implements View.OnClickListener {

    TextView tvLogOut;
    RecyclerView rvEvents;
    DBHelper mDbHelper = null;
    ArrayList<Event> mEventArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        mDbHelper = new DBHelper(this);
        mEventArrayList = new ArrayList<>();
        rvEvents = findViewById(R.id.rvEvents);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvEvents.setLayoutManager(mLayoutManager);
        rvEvents.setHasFixedSize(true);
        tvLogOut = findViewById(R.id.tvLogOut);
        tvLogOut.setOnClickListener(this);

        readEventsFromDB();

    }

    private void readEventsFromDB() {

        mEventArrayList = mDbHelper.readEventDetails();

        EventAdapter mEventAdapter = new EventAdapter(this,mEventArrayList);
        rvEvents.setAdapter(mEventAdapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tvLogOut:

                Intent intent = new Intent(this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                break;
        }
    }
}
