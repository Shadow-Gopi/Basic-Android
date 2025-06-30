package com.gopi.mynewapplication.data.database.repository;

import androidx.lifecycle.LiveData;

import com.gopi.mynewapplication.data.database.AppDatabase;
import com.gopi.mynewapplication.data.database.dao.UserDao;
import com.gopi.mynewapplication.data.database.entity.UserEntity;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    public UserRepository(AppDatabase appDatabase) {
        userDao = appDatabase.userDao();
    }

    public void insertUser(UserEntity userEntity) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insertUser(userEntity);
        });
    }

    public boolean getIsUserExists(String userName) {
        return userDao.isUserExists(userName);
    }
    public String getPassword(String userName){
        return userDao.getPassword(userName);
    }
    public LiveData<List<UserEntity>> getUsers(){
        return userDao.getUsers();
    }
}
