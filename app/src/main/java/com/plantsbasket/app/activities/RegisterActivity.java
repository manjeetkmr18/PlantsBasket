package com.plantsbasket.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.plantsbasket.app.ProgressDialog;
import com.plantsbasket.app.R;
import com.plantsbasket.app.SignUpModel;
import com.plantsbasket.app.fire_store_data.UserCollectionOperations;


public class RegisterActivity extends AppCompatActivity {
    private TextView tvLogin;
    private EditText name, email_id, password, confirmPassword;
    private Button btn_signup_now;
    private static final String TAG = "";
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private UserCollectionOperations userCollectionOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        // actionBar.setTitle("SignUp");
        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        userCollectionOperations = new UserCollectionOperations();

        tvLogin = findViewById(R.id.link_signIn);
        name = findViewById(R.id.et_name);
        email_id = findViewById(R.id.signUp_email);
        password = findViewById(R.id.signUp_password);
        confirmPassword = findViewById(R.id.signUp_password);
        btn_signup_now = findViewById(R.id.btn_signup_now);

        btn_signup_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = name.getText().toString();
                String email = email_id.getText().toString();
                String userPassword = password.getText().toString();
                String userConfirmPassword = confirmPassword.getText().toString();
                if (TextUtils.isEmpty(fullName)) {
                    name.setError("Please enter Name");

                } else if (TextUtils.isEmpty(email)) {
                    email_id.setError("Please enter Email Id");

                } else if (TextUtils.isEmpty(userPassword)) {
                    password.setError("Please enter Password");

                } else if (TextUtils.isEmpty(userConfirmPassword)) {
                    confirmPassword.setError("Please enter Confirm Password");

                } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    confirmPassword.setError("Confirm password is not correct");
                } else {
                    progressDialog.show();
                    registerUser(fullName, email, userPassword);
                }
            }
        });
        tvLogin.setOnClickListener(view -> loginNow());
    }



    private void loginNow() {
        Intent move = new Intent(this, LoginActivity.class);
        startActivity(move);
    }


    private void registerUser(String fullName, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            progressDialog.dissmiss();
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                email_id.setError("User with this email already exist.");
                            }


                        } else {
                            String userId = task.getResult().getUser().getUid();
                            addDataToFirestore(userId,fullName, email);
                        }
                    }
                });
    }

    private void addDataToFirestore(String userId, String name, String email) {
        SignUpModel signUpModel = new SignUpModel(userId, name, email , "");
        userCollectionOperations.saveOrEditUserData(signUpModel, new UserCollectionOperations.SaveUserCollectionCallback() {
            @Override
            public void saveUsersDataSuccess() {
                progressDialog.dissmiss();
                finishAffinity();
                Toast.makeText(RegisterActivity.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                Intent move = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(move);
            }

            @Override
            public void saveUsersDataFailure(String message) {
                progressDialog.dissmiss();
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}