package com.deepak.utacommunityservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class AdminDashBoardActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        findViewById(R.id.tvCreateEvent).setOnClickListener(this);
        findViewById(R.id.tvEventRegisteredUsers).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvCreateEvent:

                Intent intent = new Intent(this,CreateEventActivity.class);
                startActivity(intent);

                break;

            case R.id.tvEventRegisteredUsers:

                Intent intentOne = new Intent(this,EventRegisterUserActivity.class);
                startActivity(intentOne);


                break;

        }
    }
}
