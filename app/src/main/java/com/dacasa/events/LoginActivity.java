package com.dacasa.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginBtn;
    private TextView userRegister;
    private Intent HomeActivity;
    private ProgressBar loginProgress;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mEmailField = findViewById(R.id.email);
        mPasswordField = findViewById(R.id.password);
        userRegister = findViewById(R.id.registerLink);
        HomeActivity = new Intent(this, HomActivity.class);
        loginProgress = findViewById(R.id.progressBarLogin);
        mLoginBtn = findViewById(R.id.loginBtn);

        loginProgress.setVisibility(View.INVISIBLE);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                loginProgress.setVisibility(View.VISIBLE);
                mLoginBtn.setVisibility(View.INVISIBLE);

                String email = mEmailField.getText().toString().trim();
                String password = mPasswordField.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmailField.setError("Email is required");
                    loginProgress.setVisibility(View.INVISIBLE);
                    mLoginBtn.setVisibility(View.VISIBLE);
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPasswordField.setError("Password is required");
                    loginProgress.setVisibility(View.INVISIBLE);
                    mLoginBtn.setVisibility(View.VISIBLE);
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Redirect the user to the home screen
                                    Intent intent = new Intent(LoginActivity.this, HomActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    loginProgress.setVisibility(View.INVISIBLE);
                                    mLoginBtn.setVisibility(View.VISIBLE);
                                }
                            }
                        });
            }
        });

        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            //user is already connected so we need to redirect him to splash page
            updateUI();
        }
    }

    private void updateUI() {
        startActivity(HomeActivity);
        finish();
    }


}