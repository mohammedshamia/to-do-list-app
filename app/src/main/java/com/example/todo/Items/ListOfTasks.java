package com.example.todo.Items;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.Adapters.TaskAdapterEx;
import com.example.todo.Authentication.LoginPage;
import com.example.todo.Pages.BaseScreen;
import com.example.todo.Pages.TaskChoicesClass;
import com.example.todo.Pages.TaskClass;
import com.example.todo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfTasks extends AppCompatActivity implements TaskAdapterEx.ListItemClickListener {
    RecyclerView tasks_rv;
    TaskAdapterEx taskAdapter;
    private FirebaseAuth mAuth;
    EditText Ltitle;
    TextView lougout;
    Button AddTask;
    static java.util.List<TaskClass> categoriesTaskClass = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        tasks_rv = findViewById(R.id.tasks_rv);
        Ltitle = findViewById(R.id.Ltitle);
        AddTask = findViewById(R.id.AddTask);
        lougout = findViewById(R.id.layout);
        AddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                TaskClass newTaskClass = new TaskClass();
                newTaskClass.setTitle(Ltitle.getText().toString());
                newTaskClass.setCount(newTaskClass.getCount());
                String categoryId = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").push().getKey();
                newTaskClass.setId(categoryId);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).setValue(newTaskClass);
                Toast.makeText(ListOfTasks.this, "Category has been added successfully", Toast.LENGTH_SHORT).show();
                Ltitle.setText("");
            }
        });
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        categoriesTaskClass.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            TaskClass item = snapshot.getValue(TaskClass.class);
                            categoriesTaskClass.add(item);

                        }
                        taskAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ListOfTasks.this, "signed out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListOfTasks.this, LoginPage.class);
                startActivity(intent);

            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOfTasks.this, BaseScreen.class);
                startActivity(intent);

            }
        });
        tasks_rv = findViewById(R.id.tasks_rv);
        tasks_rv.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapterEx(categoriesTaskClass, this);
        tasks_rv.setAdapter(taskAdapter);

    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(ListOfTasks.this, TaskChoicesClass.class);
        intent.putExtra("CATEGORY_ID", categoriesTaskClass.get(position).getId());
        intent.putExtra("CATEGORY_TITLE", categoriesTaskClass.get(position).getTitle());
        startActivity(intent);
    }
}
