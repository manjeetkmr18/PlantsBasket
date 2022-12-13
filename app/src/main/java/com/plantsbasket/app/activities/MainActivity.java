package com.plantsbasket.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.plantsbasket.app.R;
import com.plantsbasket.app.activities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
            finishAffinity();
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
        }
        setContentView(R.layout.activity_main);
        final Button shop_now = findViewById(R.id.btn_shop_now);
        shop_now.setOnClickListener(view -> shopNow());
    }
    private void shopNow() {
        Intent move = new Intent(this, LoginActivity.class);
        startActivity(move);
    }
}