package com.gopi.mynewapplication.ui.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gopi.mynewapplication.data.database.AppDatabase;
import com.gopi.mynewapplication.data.database.entity.TodoEntity;
import com.gopi.mynewapplication.data.database.entity.UserEntity;
import com.gopi.mynewapplication.data.database.repository.TodoRepository;
import com.gopi.mynewapplication.data.database.repository.UserRepository;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private final TodoRepository todoRepository;
    public TodoViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        todoRepository = new TodoRepository(db);
    }
    public void insertTodo(TodoEntity todoEntity) {
        todoRepository.insertTodo(todoEntity);
    }

    public LiveData<List<TodoEntity>> getAllTodos(){
        return todoRepository.getAllTodos();
    }
    public void updateTodo(int id, String title, String detail, long date){
            todoRepository.updateTodo(id,title,detail,date);
    }

    public void deleteTodo(int id){
            todoRepository.deleteTodo(id);
    }
}
