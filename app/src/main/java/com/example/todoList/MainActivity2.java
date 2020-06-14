package com.example.todoList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity {

    EditText name1, name2, uName, password, mobNumber;
    Button btn;
    User user;
    DatabaseReference ref;
    long id = 0;
    //int flag = 0;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        uName = findViewById(R.id.uName);
        password = findViewById(R.id.password);
        mobNumber = findViewById(R.id.mobNumber);
        btn = findViewById(R.id.signButton);
        auth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name11 = name1.getText().toString().trim();
                String name22 = name2.getText().toString().trim();
                String username = uName.getText().toString().trim();
                String password1 = password.getText().toString().trim();
                String phone = mobNumber.getText().toString().trim();

                if (name11.isEmpty()) {
                    name1.setError("First Name Required");
                    name1.requestFocus();
                    return;
                }

                if (name11.length() <= 2 || name11.length() > 10) {
                    name1.setError("Length should be in range 3-10");
                    name1.requestFocus();
                    return;
                }

                if (name22.isEmpty()) {
                    name2.setError("Last Name required");
                    name2.requestFocus();
                    return;
                }
                if (name22.length() <= 2 || name22.length() > 10) {
                    name2.setError("Length should be in range 3-10");
                    name2.requestFocus();
                    return;
                }

                if (username.isEmpty()) {
                    uName.setError("UserName required");
                    uName.requestFocus();
                    return;
                }

                if (username.length() <= 2 || username.length() > 30) {
                    uName.setError("Length should be in range 3-10");
                    uName.requestFocus();
                    return;
                }
                    if (password1.isEmpty()) {
                    password.setError("UserName required");
                    password.requestFocus();
                    return;
                }

                if (password1.length() <= 7 || password1.length() > 15) {
                    password.setError("Strong Password should be in range 8-15");
                    password.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    mobNumber.setError("UserName required");
                    mobNumber.requestFocus();
                    return;
                }
                if (phone.length() != 10) {
                    mobNumber.setError("Phone number must be 10 digit long");
                    mobNumber.requestFocus();
                    return;
                }

                auth.createUserWithEmailAndPassword(username, password1).addOnCompleteListener(MainActivity2.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity2.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity2.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                }
                            }


                        }
                );

                user = new User();
                ref = FirebaseDatabase.getInstance().getReference().child("User");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    id = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


                user.setfirstName(name11);
                user.setlastName(name22);
                //user.setUsername(username);
                user.setPassword(password1);
                user.setPhoneNumber(phone);

                ref.child(String.valueOf(id + 1)).setValue(user);


            }
        });



    }
    }

