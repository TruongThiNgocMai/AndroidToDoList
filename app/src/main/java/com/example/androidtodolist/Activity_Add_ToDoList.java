package com.example.androidtodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class Activity_Add_ToDoList extends AppCompatActivity {
    AppDatabase db;
    ToDoAdapter toDoAdapter;
    String Task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add__to_do_list);

        @SuppressLint("WrongViewCast") Button btn = (Button) findViewById(R.id.btnBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Add_ToDoList.this, MainActivity.class);
                startActivity(intent);
            }
        });

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        final Button btn_Add_Task = (Button) findViewById(R.id.btn_AddTask);

        btn_Add_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
                finish();
            }
        });
    }

    public void addTask() {
        final EditText edit_text_Title = (EditText) findViewById(R.id.edit_text_Title);
        final EditText edit_text_Content = (EditText) findViewById(R.id.edit_text_Content);
        final EditText edit_text_Date = (EditText) findViewById(R.id.edit_text_Date);

        Task = edit_text_Title.getText().toString();
        Task = edit_text_Content.getText().toString();
        Task = edit_text_Date.getText().toString();

        if (Task.isEmpty()) {
            Toast.makeText(this, "Task must not null", Toast.LENGTH_SHORT).show();
            return;
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                ToDo task = new ToDo();
                task.setTitle(Task);
                task.setContent(Task);
                task.setDatetime(Task);
                db.toDoDao().insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(Activity_Add_ToDoList.this, Task + " has been added successfully", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}
