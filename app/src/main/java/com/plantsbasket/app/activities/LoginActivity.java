package com.plantsbasket.app.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.plantsbasket.app.R;
import com.plantsbasket.app.SignUpModel;
import com.plantsbasket.app.fire_store_data.UserCollectionOperations;

public class LoginActivity extends AppCompatActivity {
    private TextView signUp, tvForgot_password;
    private Button btn_login;
    private static final String TAG = "";
    private EditText inputEmail, inputPassword;
    private FirebaseAuth mAuth;
    private ImageView sign_in_google;
    private SignInButton button;
    private ProgressDialog progressDialog;
    private final static int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
    private UserCollectionOperations userCollectionOperations;

   /* @Override
    protected void onStart() {

        super.onStart();

        mAuth.addAuthStateListener(mAuthListner);

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        userCollectionOperations = new UserCollectionOperations();
        ActionBar actionBar = getSupportActionBar();
        // actionBar.setTitle("Login");
        actionBar.hide();
        inputEmail = findViewById(R.id.login_email);
        inputPassword = findViewById(R.id.login_password);
        sign_in_google = findViewById(R.id.sign_in_google);
        signUp = findViewById(R.id.link_signup);
        tvForgot_password = findViewById(R.id.forgot_password);
        btn_login = findViewById(R.id.btn_login);
        signUp.setOnClickListener(view -> signUpNow());
        tvForgot_password.setOnClickListener(view -> forgotPasswordNow());
        sign_in_google.setOnClickListener(view -> signInWithGoogle());
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
    private void signInWithGoogle(){
        signIn();
    }

    private void loginNow() {

        String email = inputEmail.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email id", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();
        //authenticate user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // there was an error
                            Log.d(TAG, "signInWithEmail:success");
                            finishAffinity();
                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "singInWithEmail:Fail");
                            Toast.makeText(LoginActivity.this, getString(R.string.failed), Toast.LENGTH_LONG).show();
                        }

                        progressDialog.dismiss();
                    }

                });

    }
    private void signIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                progressDialog.show();
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                progressDialog.dismiss();
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            userCollectionOperations.checkIfUserPresentInUsersCollection(user.getUid(), new UserCollectionOperations.CheckIfUserPresentCallback() {
                                @Override
                                public void checkIfUserPresentResult(boolean isPresent) {
                                    if(isPresent){
                                        progressDialog.dismiss();
                                        finishAffinity();
                                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                    }else{
                                        SignUpModel signUpModel = new SignUpModel(user.getUid(), user.getDisplayName(), user.getEmail(), "");
                                       userCollectionOperations.saveOrEditUserData(signUpModel, new UserCollectionOperations.SaveUserCollectionCallback() {
                                           @Override
                                           public void saveUsersDataSuccess() {
                                               progressDialog.dismiss();
                                               finishAffinity();
                                               startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                           }

                                           @Override
                                           public void saveUsersDataFailure(String message) {
                                               progressDialog.dismiss();
                                               Toast.makeText(LoginActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                           }
                                       });
                                    }
                                }
                            });
                            //if(user.i)
                            //updateUI(user);
                        } else {
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Aut Fail", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

}