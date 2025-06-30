package com.gopi.mynewapplication.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gopi.mynewapplication.data.database.AppDatabase;
import com.gopi.mynewapplication.data.database.entity.UserEntity;
import com.gopi.mynewapplication.data.database.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    public UserViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        userRepository = new UserRepository(db);
    }
    public LiveData<List<UserEntity>> getUsers(){
        return userRepository.getUsers();
    }
}
