package com.gopi.mynewapplication.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gopi.mynewapplication.data.database.dao.SpeechDao;
import com.gopi.mynewapplication.data.database.dao.TodoDao;
import com.gopi.mynewapplication.data.database.dao.UserDao;
import com.gopi.mynewapplication.data.database.entity.SpeechEntity;
import com.gopi.mynewapplication.data.database.entity.TodoEntity;
import com.gopi.mynewapplication.data.database.entity.UserEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserEntity.class, TodoEntity.class, SpeechEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {


    public abstract UserDao userDao();

    public abstract TodoDao todoDao();
    public abstract SpeechDao speechDao();

    private static volatile AppDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newSingleThreadExecutor();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "app_database"
                            ).fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
