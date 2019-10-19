package com.example.androidtodolist;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { ToDo.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ToDoDao toDoDao();
}
