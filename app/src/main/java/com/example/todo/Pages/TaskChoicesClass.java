package com.example.todo.Pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.Adapters.CheckTaskAdapter;
import com.example.todo.Items.InformationTask;
import com.example.todo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskChoicesClass extends AppCompatActivity implements CheckTaskAdapter.ListItemClickListener {
    RecyclerView choice_rv;
    CheckTaskAdapter  taskAdapter;
  ImageView addTask;
    EditText TaskDescription, TaskTitle;
    String categoryId,categoryTitle;
    TextView cateTitle,delete;
    private FirebaseAuth mAuth;
    static List<CheckTaskClass> CheckList = new ArrayList<>();
    int count;
    boolean flag =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_choices);
        Bundle extras = getIntent().getExtras();
        categoryId = extras.getString("CATEGORY_ID");
        categoryTitle=extras.getString("CATEGORY_TITLE");
        cateTitle=findViewById(R.id.cateTitle);
        cateTitle.setText(categoryTitle);
        delete=findViewById(R.id.delete);
        addTask=findViewById(R.id.addTask);
        TaskDescription = findViewById(R.id.TaskDescription);
        TaskTitle = findViewById(R.id.TaskTitle);
        addTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                CheckTaskClass newTask = new CheckTaskClass();
                newTask.setTitle(TaskTitle.getText().toString());
                newTask.setDescription(TaskDescription.getText().toString());
                newTask.setIsChecked(false);
                String taskId = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).child("innerTask").push().getKey();
                newTask.setId(taskId);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).child("innerTask").child(taskId).setValue(newTask);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot: dataSnapshot.getChildren() ){
                                    TaskClass taskClass =  snapshot.getValue(TaskClass.class);
                                    if(taskClass.getId().compareTo(categoryId) == 0 && flag){
                                        count = taskClass.getCount() ;
                                        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).child("count").setValue(++count);
                                        flag = false;
//                                        Intent intent = new Intent(TaskChoices.this, com.example.todo.List.class);
//                                        startActivity(intent);
                                        break;
                                    }

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                            }
                        });
                flag=true;
                Toast.makeText(TaskChoicesClass.this,"added successfully", Toast.LENGTH_SHORT).show();
                TaskTitle.setText("");
                TaskDescription.setText("");

            }
        });
        findViewById(R.id.back1).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).removeValue();
                finish();
            }
        });
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).child("innerTask")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        CheckList.clear();
                        for(DataSnapshot snapshot: dataSnapshot.getChildren() ){
                            CheckTaskClass item =  snapshot.getValue(CheckTaskClass.class);
                            CheckList.add(item);
                        }
                        taskAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


        choice_rv = findViewById(R.id.choice_rv);
        choice_rv.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new CheckTaskAdapter (this,CheckList,  this);
        choice_rv.setAdapter(taskAdapter);

    }


    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(TaskChoicesClass.this, InformationTask.class);
        intent.putExtra("Task_Id", CheckList.get(position).getId());
        intent.putExtra("Task_Title", CheckList.get(position).getTitle());
        intent.putExtra("Task_Description", CheckList.get(position).getDescription());
        intent.putExtra("Category_Title", categoryTitle);
        intent.putExtra("Category_Id", categoryId);
        startActivity(intent);
    }
}
