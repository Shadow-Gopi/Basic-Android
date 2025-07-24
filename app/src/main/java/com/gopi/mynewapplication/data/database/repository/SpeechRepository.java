package com.gopi.mynewapplication.data.database.repository;

import androidx.lifecycle.LiveData;

import com.gopi.mynewapplication.data.database.AppDatabase;
import com.gopi.mynewapplication.data.database.dao.SpeechDao;
import com.gopi.mynewapplication.data.database.entity.SpeechEntity;
import com.gopi.mynewapplication.data.database.entity.TodoEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpeechRepository {

    private final SpeechDao speechDao;
    private final ExecutorService executorService;

    public SpeechRepository(SpeechDao speechDao) {
        this.speechDao = speechDao;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void insertText(String text) {
        executorService.execute(() -> speechDao.insertText(new SpeechEntity(text)));
    }

    public LiveData<List<SpeechEntity>> getAllSpeechTexts(){
        return speechDao.getAllSpeechTexts();
    }

    public void deleteById(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            speechDao.deleteById(id);
        });
    }
}
