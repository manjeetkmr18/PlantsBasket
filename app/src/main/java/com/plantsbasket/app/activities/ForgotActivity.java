package com.plantsbasket.app.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.plantsbasket.app.R;

public class ForgotActivity extends AppCompatActivity {
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        ActionBar actionBar = getSupportActionBar();
         actionBar.setTitle("Forgot Password");
        actionBar.hide();
        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(view -> submitNow());
    }
    private void submitNow() {
        Intent move = new Intent(this, RegisterActivity.class);
        startActivity(move);
    }
}