package com.example.todo.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.todo.Authentication.LoginPage;
import com.example.todo.Items.ListOfTasks;
import com.example.todo.R;
import com.google.firebase.auth.FirebaseAuth;

public class BaseScreen extends AppCompatActivity {
    TextView BtnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnNext=findViewById(R.id.BtnNext);
        BtnNext.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent;
                if(null != FirebaseAuth.getInstance().getCurrentUser()){
                    intent = new Intent(BaseScreen.this, ListOfTasks.class);

                }else {
                    intent = new Intent(BaseScreen.this, LoginPage.class);
                }
                startActivity(intent);
            }
        });

    }
}