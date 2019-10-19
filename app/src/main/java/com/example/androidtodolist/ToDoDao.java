package com.example.androidtodolist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ToDoDao {
    @Query("SELECT * FROM ToDo")
    List<ToDo> getAll();

    @Insert
    void insert(ToDo toDo);

    @Delete
    void delete(ToDo toDo);

    @Update
    void updateOne(ToDo toDo);

}
