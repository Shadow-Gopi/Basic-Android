package com.gopi.mynewapplication.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.gopi.mynewapplication.data.database.entity.TodoEntity;

import java.util.List;

@Dao
public interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTodo(TodoEntity todoEntity);

    @Query("SELECT * FROM Todos")
    LiveData<List<TodoEntity>> getAllTodos();

    @Query("UPDATE todos SET title = :title, detail = :detail, date = :date WHERE id = :id")
    void updateTodo(int id, String title, String detail, long date);

    @Query("DELETE FROM todos WHERE id = :id")
    void deleteTodo(int id);

}
