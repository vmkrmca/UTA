package com.deepak.utacommunityservice;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventRegisterUserActivity extends Activity implements EventRequestListener{


    RecyclerView rvEvents;
    DBHelper mDbHelper;
    TextView tvNoRequest;
    ArrayList<EventRequest> mEventRequestArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_even_registered_users);

        tvNoRequest =  findViewById(R.id.tvNoRequest);
        mEventRequestArrayList = new ArrayList<EventRequest>();
        mDbHelper = new DBHelper(this);
        rvEvents = findViewById(R.id.rvEvents);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvEvents.setLayoutManager(mLayoutManager);
        rvEvents.setHasFixedSize(true);
        mEventRequestArrayList = mDbHelper.readEventRequestDetails();

        if (mEventRequestArrayList != null && mEventRequestArrayList.size() >0){

            EventsApprovalAdapter adapter = new EventsApprovalAdapter(this,mEventRequestArrayList,this);
            rvEvents.setAdapter(adapter);
            tvNoRequest.setVisibility(View.GONE);
            rvEvents.setVisibility(View.VISIBLE);

        }else{
            tvNoRequest.setVisibility(View.VISIBLE);
            rvEvents.setVisibility(View.GONE);
        }

    }

    @Override
    public void onEventRequest(EventRequest mEventRequest, int requestValue) {

        if (requestValue == 1001) {
            // APPROVAL

            sendEmailToParticipant(mEventRequest.emailAddress,mEventRequest.eventTitle,"We are Approval Your Request,Thank You");

            Toast.makeText(this,"Email Sent to "+mEventRequest.emailAddress,Toast.LENGTH_SHORT).show();



        }else if (requestValue == 1002) {

            // DIS APPROVAL
            sendEmailToParticipant(mEventRequest.emailAddress,mEventRequest.eventTitle,"We are Sorry we are not able to Approval Your Request,Thank You");

            Toast.makeText(this,"Email Sent to "+mEventRequest.emailAddress,Toast.LENGTH_SHORT).show();
        }
    }

    public void sendEmailToParticipant(String addresses, String eventTitle,String subject) {
        
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{addresses});
        i.putExtra(Intent.EXTRA_SUBJECT, eventTitle);
        i.putExtra(Intent.EXTRA_TEXT   ,  subject);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(EventRegisterUserActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
