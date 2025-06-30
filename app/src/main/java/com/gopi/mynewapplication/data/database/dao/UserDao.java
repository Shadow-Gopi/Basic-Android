package com.gopi.mynewapplication.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.gopi.mynewapplication.data.database.entity.UserEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity userEntity);

    @Query("SELECT EXISTS(SELECT 1 FROM USER WHERE userName = :userName)")
    boolean isUserExists(String userName);

    @Query("SELECT password FROM USER WHERE userName = :userName")
    String getPassword(String userName);

    @Query("SELECT * FROM USER")
    LiveData<List<UserEntity>> getUsers();
}
