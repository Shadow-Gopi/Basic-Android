package com.gopi.mynewapplication.data.database.repository;

import androidx.lifecycle.LiveData;

import com.gopi.mynewapplication.data.database.AppDatabase;
import com.gopi.mynewapplication.data.database.dao.TodoDao;
import com.gopi.mynewapplication.data.database.entity.TodoEntity;

import java.util.List;


public class TodoRepository {
    private final TodoDao todoDao;

    public TodoRepository(AppDatabase appDatabase) {
        todoDao= appDatabase.todoDao();
    }

    public void insertTodo(TodoEntity todoEntity) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            todoDao.insertTodo(todoEntity);
        });
    }

    public void updateTodo(int id, String title, String detail, long date){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            todoDao.updateTodo(id,title,detail,date);
        });
    }

    public void deleteTodo(int id){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            todoDao.deleteTodo(id);
        });
    }

    public LiveData<List<TodoEntity>> getAllTodos(){
        return todoDao.getAllTodos();
    }
}

