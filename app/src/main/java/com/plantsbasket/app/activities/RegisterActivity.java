package com.plantsbasket.app.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.plantsbasket.app.R;
import com.plantsbasket.app.activities.LoginActivity;

public class RegisterActivity extends AppCompatActivity {
    private TextView tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        // actionBar.setTitle("SignUp");
        actionBar.hide();
        tvLogin = findViewById(R.id.link_signIn);
        tvLogin.setOnClickListener(view -> loginNow());
    }
    private void loginNow() {
        Intent move = new Intent(this, LoginActivity.class);
        startActivity(move);
    }
}