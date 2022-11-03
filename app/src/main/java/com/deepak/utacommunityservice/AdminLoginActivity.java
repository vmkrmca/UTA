package com.deepak.utacommunityservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AdminLoginActivity extends Activity implements View.OnClickListener {

    EditText etUserName,etPassword;
    TextView tvLogin,tvCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etUserPassword);

        tvLogin = findViewById(R.id.tvLogin);
        tvCancel = findViewById(R.id.tvCancel);

        tvLogin.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tvLogin:

                    String userName = etUserName.getText().toString();
                    String password = etPassword.getText().toString();

                    if (userName.equals("Admin") && password.equals("Admin")){

                        Intent intent = new Intent(this,AdminDashBoardActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(this,"UserName and Password is InCorrect",Toast.LENGTH_SHORT).show();
                    }

                break;
            case R.id.tvCancel:
                etUserName.setText("");
                etPassword.setText("");
                break;
        }
    }
}
