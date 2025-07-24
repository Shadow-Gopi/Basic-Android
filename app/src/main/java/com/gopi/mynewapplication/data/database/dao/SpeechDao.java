package com.gopi.mynewapplication.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.gopi.mynewapplication.data.database.entity.SpeechEntity;

import java.util.List;

@Dao
public interface SpeechDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertText(SpeechEntity speechEntity);


    @Query("SELECT * FROM speech_table ORDER BY id DESC")
    LiveData<List<SpeechEntity>> getAllSpeechTexts();

    @Query("DELETE FROM Speech_Table WHERE id = :id")
    void deleteById(int id);

}
