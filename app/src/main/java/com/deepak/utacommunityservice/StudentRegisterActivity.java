package com.deepak.utacommunityservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StudentRegisterActivity extends Activity implements View.OnClickListener {

    EditText etMobileNumber,etFirstName,etLastName,etPassword,etCPassword,etEmailAddress;
    TextView tvRegister,tvCancel;
    ArrayList<Student> mStudentArrayList = new ArrayList<>();
    DBHelper mDBDbHelper = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        mDBDbHelper = new DBHelper(StudentRegisterActivity.this);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPassword = findViewById(R.id.etPassword);
        etCPassword = findViewById(R.id.etCPassword);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etEmailAddress = findViewById(R.id.etEmail);

        tvRegister  = findViewById(R.id.tvRegister);
        tvCancel = findViewById(R.id.tvCancel);

        tvRegister.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tvRegister:

                    String firstName = etFirstName.getText().toString();
                    String lastName = etLastName.getText().toString();
                    String password = etPassword.getText().toString();
                    String cPassword = etCPassword.getText().toString();
                    String mobileNumber = etMobileNumber.getText().toString();
                    String emailAddress = etEmailAddress.getText().toString();

                    if (firstName.isEmpty()) {
                        Toast.makeText(this,"Please Enter First Name",Toast.LENGTH_SHORT).show();
                    }else if (lastName.isEmpty()){
                        Toast.makeText(this,"Please Enter Last Name",Toast.LENGTH_SHORT).show();
                    } else if (mobileNumber.isEmpty()){
                        Toast.makeText(this,"Please Enter MobileNumber",Toast.LENGTH_SHORT).show();
                    } else if (password.isEmpty()){
                        Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    } else if (cPassword.isEmpty()){
                        Toast.makeText(this,"Please Enter Confirm Password",Toast.LENGTH_SHORT).show();
                    }  else if (emailAddress.isEmpty()){
                        Toast.makeText(this,"Please Enter Email Address",Toast.LENGTH_SHORT).show();
                    } else if (password.equals(cPassword)) {

                        Student mStudent = new Student();
                        mStudent.setFirstName(firstName);
                        mStudent.setLastName(lastName);
                        mStudent.setEmailAddress(emailAddress);
                        mStudent.setPassword(password);
                        mStudent.setMobileNumber(mobileNumber);

                        long id = mDBDbHelper.insertStudentRecord(mStudent);

                        if (id != -1){
                            Intent intent = new Intent(this,StudentLoginActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(this,"MobileNumber is Already Exists",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this,"Password and Confirm Password is not Matches",Toast.LENGTH_SHORT).show();
                    }

                break;
            case R.id.tvCancel:

                        etFirstName.setText("");
                        etLastName.setText("");
                        etPassword.setText("");
                        etCPassword.setText("");
                        etMobileNumber.setText("");
                        etEmailAddress.setText("");

                break;

        }
    }
}
