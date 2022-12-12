package com.example.sqlite;


import android.database.Cursor;

public interface Repository{
    Cursor readAllData();
    void updateData (String row_id, String recipe, String ingredients, String chef);
    void deleteOneRow(String row_id);
    void deleteAllData();
    void addIem(String recipe, String ingredients, String chef);
}