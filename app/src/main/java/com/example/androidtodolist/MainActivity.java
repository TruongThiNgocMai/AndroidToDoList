package com.example.androidtodolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ToDoAdapter.OnItemClicked {
    RecyclerView recyclerviewUser;
    AppDatabase db;
    ToDoAdapter toDoAdapter;
    public static List<ToDo> Tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        recyclerviewUser = findViewById(R.id.recyclerview_id);
        recyclerviewUser.setLayoutManager(new LinearLayoutManager((this)));

        final Button btn_Add = (Button) findViewById(R.id.btn_ADD);

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertConfirm("Cofirm", "Would you like to add a new task ");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getandDisplayTask();
    }

    @SuppressLint("StaticFieldLeak")
    public void getandDisplayTask() {
        new AsyncTask<Void, Void, List<ToDo>>() {
            @Override
            protected List<ToDo> doInBackground(Void... voids) {
                Tasks = db.toDoDao().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toDoAdapter = new ToDoAdapter(this, Tasks);
                        toDoAdapter.setOnClick(MainActivity.this);
                        recyclerviewUser.setAdapter(toDoAdapter);
                    }
                });
                return null;
            }
        }.execute();
    }

    private void openAddTodoScreen() {
        Intent intent = new Intent(MainActivity.this, Activity_Add_ToDoList.class);
        startActivity(intent);
    }

    private void openUpdateTodoScreen(ToDo todo) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        intent.putExtra("id", todo.getId());
        intent.putExtra("task", todo.getTitle());
        intent.putExtra("content", todo.getContent());
        intent.putExtra("date", todo.getDatetime());

        startActivity(intent);
    }

    @Override
    public void onClickItemUpdate(int position) {
        openUpdateTodoScreen(Tasks.get(position));
    }

    @Override
    public void onClickItemDelete(final int position) {
        Log.i("TAG", "clicked at " + position);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.toDoDao().delete(Tasks.get(position));
                Log.i("TAG", "delete success");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                toDoAdapter.Tasks.remove(position);
                toDoAdapter.notifyDataSetChanged();
            }
        }.execute();
    }


    private void showAlertConfirm(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openAddTodoScreen();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}






