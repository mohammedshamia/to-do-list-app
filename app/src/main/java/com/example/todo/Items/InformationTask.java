package com.example.todo.Items;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.Pages.EditTaskPage;
import com.example.todo.Pages.TaskClass;
import com.example.todo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InformationTask extends AppCompatActivity {
    TextView taskName,taskDescription,categoryList,deleteTask,edit;
    String taskId,taskTitle,taskDes,categoryId,categoryName;
    FirebaseAuth mAuth;
    boolean flag=true;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Bundle extras = getIntent().getExtras();
        taskId = extras.getString("Task_Id");
        taskTitle=extras.getString("Task_Title");
        taskDes=extras.getString("Task_Description");
        categoryName=extras.getString("Category_Title");
        categoryId=extras.getString("Category_Id");
        taskName=findViewById(R.id.taskName);
        taskDescription=findViewById(R.id.taskDescription);
        categoryList=findViewById(R.id.categoryList);
        deleteTask=findViewById(R.id.deleteTask);
        taskName.setText(taskTitle);
        taskDescription.setText(taskDes);
        categoryList.setText(categoryName);
        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).child("innerTask").child(taskId).removeValue();
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot: dataSnapshot.getChildren() ){
                                    TaskClass taskClass =  snapshot.getValue(TaskClass.class);
                                    if(taskClass.getId().compareTo(categoryId) == 0 && flag){
                                        count = taskClass.getCount() -1;
                                        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).child("count").setValue(count);
                                        flag = false;

                                        break;
                                    }

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                            }
                        });
                finish();
                findViewById(R.id.back).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
              finish();

                    }
                });
            }
        });
        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(InformationTask.this, EditTaskPage.class);
                intent.putExtra("Task_Id", taskId);
                intent.putExtra("Task_Title", taskTitle);
                intent.putExtra("Task_Description", taskDes);
                intent.putExtra("CATEGORY_Name", categoryName);
                intent.putExtra("CATEGORY_ID", categoryId);
                startActivity(intent);
            }
        });

    }
}