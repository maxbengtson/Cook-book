package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class RepositoryImplementation extends SQLiteOpenHelper implements Repository {

    private Context context;
    private static final String DATABASE_NAME = "Recipes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_recipes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_RECIPE = "item_title";
    private static final String COLUMN_INGREDIENTS = "item_ingredients";
    private static final String COLUMN_CHEF = "item_measurement";


    RepositoryImplementation(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_RECIPE + " TEXT, " +
                        COLUMN_INGREDIENTS + " TEXT, " +
                        COLUMN_CHEF + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void addIem(String recipe, String ingredients, String chef){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RECIPE, recipe);
        cv.put(COLUMN_INGREDIENTS, ingredients);
        cv.put(COLUMN_CHEF, chef);
        long result = db.insert(TABLE_NAME, null, cv);
        
        //Om applikationen inte kan föra in uppgifterna är resultatet -1
        if (result == -1){
            Toast.makeText(context, R.string.failed_to_add, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, R.string.addes_successfully, Toast.LENGTH_SHORT).show();

        }
    }
    public Cursor readAllData(){
         String query = "SELECT * FROM " + TABLE_NAME;
         SQLiteDatabase db = this.getReadableDatabase();

         Cursor cursor = null;
         if (db != null){
             cursor = db.rawQuery(query, null);
         }
         return cursor;
    }
    public void updateData(String row_id, String recipe, String ingredients, String chef){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RECIPE, recipe);
        cv.put(COLUMN_INGREDIENTS, ingredients);
        cv.put(COLUMN_CHEF, chef);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, R.string.failed_to_update, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, R.string.updated_successfully, Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, R.string.failed_to_delete, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, R.string.deleted_successfully, Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
