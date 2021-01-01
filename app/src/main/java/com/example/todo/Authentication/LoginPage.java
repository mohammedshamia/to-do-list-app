package com.example.todo.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.todo.Items.ListOfTasks;
import com.example.todo.R;
import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class LoginPage extends AppCompatActivity {
    EditText username,password;
    Button loginButton;
    TextView forget;
    TextView CreateProfile;

    FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        forget = findViewById(R.id.forget);
        username = findViewById(R.id.username);
        password= findViewById(R.id.password);
        loginButton = findViewById(R.id.signin);
        CreateProfile=findViewById(R.id.CreateProfile);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPage.this.doSignIn(username.getText().toString(), password.getText().toString());
            }
        });
        CreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
            }
        });
    }
    private void doSignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            Intent intent = new Intent(LoginPage.this, ListOfTasks.class);
                            startActivity(intent);
//
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}