package com.gopi.mynewapplication.ui.speach;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gopi.mynewapplication.data.database.AppDatabase;
import com.gopi.mynewapplication.data.database.entity.SpeechEntity;
import com.gopi.mynewapplication.data.database.repository.SpeechRepository;

import java.util.List;

public class SpeechViewModel extends AndroidViewModel {

    private final SpeechRepository speechRepository;

    public SpeechViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application); // same as LoginViewModel
        speechRepository = new SpeechRepository(db.speechDao());
    }

    public void insertText(String text) {
        speechRepository.insertText(text);
    }

    public LiveData<List<SpeechEntity>> getAllSpeech() {
        return speechRepository.getAllSpeechTexts();
    }

    public void deleteById(int id) {
        speechRepository.deleteById(id);
    }

}
