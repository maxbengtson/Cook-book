package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Declarera View och vår DataBaseHelper klassen
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageView;
    TextView no_data;

    Repository repository;
    Adapter adapter;

    ArrayList<String> item_id, item_recipe, item_ingredients, item_chef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageView = findViewById(R.id.empty_imageView);
        no_data = findViewById(R.id.textView);

        add_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class );
            startActivity(intent);
        });

        repository = new RepositoryImplementation(getApplicationContext());
        item_id = new ArrayList<>();
        item_recipe = new ArrayList<>();
        item_ingredients = new ArrayList<>();
        item_chef = new ArrayList<>();

        storeDataInArrays();

        //Initialiserar adaptern med all data som är sparad.
        adapter = new Adapter(MainActivity.this, this, item_id, item_recipe, item_ingredients, item_chef);

        //Sets the adapter.
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        //Lagrar innehållet från readAllData metoden i vår cursor objekt.
        Cursor cursor = repository.readAllData();

        //Om innehållet är tomt, visa "No ingredients" text
        if (cursor.getCount() == 0){
            empty_imageView.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                item_id.add(cursor.getString(0));
                item_recipe.add(cursor.getString(1));
                item_ingredients.add(cursor.getString(2));
                item_chef.add(cursor.getString(3));
            }
            empty_imageView.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    // Valet att ta bort allt.
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_all);
        builder.setMessage(R.string.delete_message);
        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            Repository repository = new RepositoryImplementation(MainActivity.this);
            repository.deleteAllData();

            //Refreshar activityn
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        // Möjlighet att ångra sig
        builder.setNegativeButton(R.string.no, (dialog, which) -> {

        });
        builder.create().show();
    }
    
}