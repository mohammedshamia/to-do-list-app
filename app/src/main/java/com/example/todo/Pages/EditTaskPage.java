package com.example.todo.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todo.Items.ListOfTasks;
import com.example.todo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class EditTaskPage extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText editTask, editDescription,categoryList;
    String taskId, categoryId,categoryTitle,taskDes,taskNAme;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Bundle extras = getIntent().getExtras();
        taskId = extras.getString("Task_Id");
        categoryId = extras.getString("CATEGORY_ID");
        categoryTitle=extras.getString("CATEGORY_Name");
        taskDes=extras.getString("Task_Description");
        taskNAme=extras.getString("Task_Title");
        editTask=findViewById(R.id.editTask);
        editDescription=findViewById(R.id.editDescription);
        categoryList=findViewById(R.id.categoryList);
        categoryList.setText(categoryTitle);
        editDescription.setText(taskDes);
        editTask.setText(taskNAme);
        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                System.out.println(taskId);
                System.out.println(categoryId);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).child("innerTask").child(taskId).child("title").setValue(editTask.getText().toString());
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).child("innerTask").child(taskId).child("description").setValue(editDescription.getText().toString());
                Toast.makeText(EditTaskPage.this,"Modified done", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditTaskPage.this, ListOfTasks.class);
                startActivity(intent);
            }
        });



    }
}