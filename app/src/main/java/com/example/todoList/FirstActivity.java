package com.example.todoList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        auth = FirebaseAuth.getInstance();
        Button button = findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser currentUser = auth.getCurrentUser();

                if (currentUser!=null){
                Intent intent = new Intent(FirstActivity.this,MainActivity.class);
                startActivity(intent);
            }else {
                    Intent intent = new Intent(FirstActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}
