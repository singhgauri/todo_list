package com.example.todoList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email, passWrd;
    Button loginBtn,registerBtn;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        passWrd = findViewById(R.id.passWrd);
        loginBtn = findViewById(R.id.loginButton);
        registerBtn = findViewById(R.id.registerButton);
        auth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity2.class);
                startActivity(intent);
                finish();
            }

        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = email.getText().toString().trim();
                String password = passWrd.getText().toString().trim();

                if (email1.isEmpty()) {
                    email.setError("Please enter your email");
                    email.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passWrd.setError("Please enter your password");
                    passWrd.requestFocus();
                    return;
                }

                auth.signInWithEmailAndPassword(email1, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Log.d("TAG", "signInWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();
                                    updateUI(user);
                                } else {

                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Invalid username or password.",
                                            Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        });

    }
    public void  updateUI(FirebaseUser currentUser){
        if(currentUser != null){
            startActivity(new Intent(this, MainActivity.class));
        }

    }}