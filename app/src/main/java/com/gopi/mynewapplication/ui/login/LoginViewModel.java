package com.gopi.mynewapplication.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gopi.mynewapplication.data.database.AppDatabase;
import com.gopi.mynewapplication.data.database.entity.UserEntity;
import com.gopi.mynewapplication.data.database.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        userRepository = new UserRepository(db);
    }

    public void insertUser(UserEntity userEntity) {
        userRepository.insertUser(userEntity);
    }

    public boolean getIsUserExists(String userName) {
        return userRepository.getIsUserExists(userName);
    }
    public String getPassword(String userName){
        return userRepository.getPassword(userName);
    }
}
