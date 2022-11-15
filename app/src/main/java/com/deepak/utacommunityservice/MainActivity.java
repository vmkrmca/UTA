package com.deepak.utacommunityservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView tvAdmin,tvStudent;

    String mobileNumber;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tvAdmin = findViewById(R.id.tvAdmin);
        tvStudent = findViewById(R.id.tvStudent);

        tvAdmin.setOnClickListener(this);
        tvStudent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvStudent: {

                mSharedPreferences = getSharedPreferences("StudentPref",MODE_PRIVATE);
                mobileNumber = mSharedPreferences.getString("MOBILE","");

                Intent intent;
                if (mobileNumber != null && !mobileNumber.isEmpty()) {
                    intent = new Intent(this, StudentDashBoard.class);
                }else{
                    intent = new Intent(this, StudentLoginActivity.class);
                }
                startActivity(intent);

                break;
            }

            case R.id.tvAdmin: {

                Intent intent = new Intent(this,AdminLoginActivity.class);
                startActivity(intent);

                break;
            }

        }
    }
}