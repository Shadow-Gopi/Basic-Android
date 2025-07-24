package com.gopi.mynewapplication.data.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Speech_Table")
public class SpeechEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String text;

    public SpeechEntity(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
