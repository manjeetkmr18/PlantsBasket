package com.plantsbasket.app.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.plantsbasket.app.R;

public class LoginActivity extends AppCompatActivity {
    private TextView signUp,tvForgot_password;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
       // actionBar.setTitle("Login");
        actionBar.hide();
        signUp = findViewById(R.id.link_signup);
        tvForgot_password = findViewById(R.id.forgot_password);
        btn_login = findViewById(R.id.btn_login);
        signUp.setOnClickListener(view -> signUpNow());
        tvForgot_password.setOnClickListener(view -> forgotPasswordNow());
        btn_login.setOnClickListener(view -> loginNow());
    }
    private void signUpNow() {
        Intent move = new Intent(this, RegisterActivity.class);
        startActivity(move);
    }
    private void forgotPasswordNow() {
        Intent move = new Intent(this, ForgotActivity.class);
        startActivity(move);
    }
    private void loginNow() {
        Intent move = new Intent(this, Dashboard_Activity.class);
        startActivity(move);
    }
}