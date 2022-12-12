package com.example.sqlite;

import android.content.Context;
import android.database.Cursor;

public interface Repository{
    Cursor readAllData();
    void updateData (String row_id, String title, String ingredients, String measurement);
    void deleteOneRow(String row_id);
    void deleteAllData();
    void addIem(String title, String ingredients, String measurement);
}