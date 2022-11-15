package com.deepak.utacommunityservice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentDashBoard extends Activity implements View.OnClickListener, RequestEventClickListener {

    TextView tvLogOut, tvEvents,tvUserName;
    LinearLayout llEventData;
    RecyclerView rvEvents;
    DBHelper mDbHelper = null;
    ArrayList<Event> mEventArrayList;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    String mobileNumber = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);


        mSharedPreferences = getSharedPreferences("StudentPref", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mobileNumber = mSharedPreferences.getString("MOBILE", "");


        llEventData = findViewById(R.id.llEventData);
        tvUserName = findViewById(R.id.tvUserName);
        tvEvents = findViewById(R.id.tvEvents);
        mDbHelper = new DBHelper(this);
        mEventArrayList = new ArrayList<>();
        rvEvents = findViewById(R.id.rvEvents);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvEvents.setLayoutManager(mLayoutManager);
        rvEvents.setHasFixedSize(true);
        tvLogOut = findViewById(R.id.tvLogOut);
        tvLogOut.setOnClickListener(this);

        Student mStudent = mDbHelper.readDataFromMobileNumber(mobileNumber);

        tvUserName.setText("Welcome Mr/Mrs."+mStudent.firstName + "" + mStudent.lastName);

        readEventsFromDB();

    }

    private void readEventsFromDB() {

        mEventArrayList = mDbHelper.readEventDetails();

        if (mEventArrayList != null && mEventArrayList.size() > 0) {
            EventAdapter mEventAdapter = new EventAdapter(this, mEventArrayList, this);
            rvEvents.setAdapter(mEventAdapter);
        } else {
            llEventData.setVisibility(View.GONE);
            tvEvents.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvLogOut:

                mEditor.putString("MOBILE","");
                mEditor.clear();
                mEditor.commit();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                break;
        }
    }

    @Override
    public void onRequestClick(Event mEvent) {

        mobileNumber = mSharedPreferences.getString("MOBILE", "");
        Student mStudent = mDbHelper.readDataFromMobileNumber(mobileNumber);
        String emailAddress = mStudent.emailAddress;
        String eventTitle = mEvent.eventTitle;
        String eventDate = mEvent.eventDate;
        String eventTime = mEvent.eventTime;
        long id = mDbHelper.insertRequestEventDetails(emailAddress, mobileNumber, eventTitle, eventDate, eventTime);

        if (id != -1) {

            Toast.makeText(this, "Request is sent to Admin", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Some thing Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
