package com.example.sqlite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    EditText title_update, ingredient_update, measurement_update;
    Button update_button, delete_button;

    String id, title, ingredient, measurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_update = findViewById(R.id.recipe_update);
        ingredient_update = findViewById(R.id.ingredient_update);
        measurement_update = findViewById(R.id.measurement_update);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();


        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setTitle(title);
        }

        update_button.setOnClickListener(view -> {
            Repository repository = new RepositoryImplementation(UpdateActivity.this);
            title= title_update.getText().toString().trim();
            ingredient = ingredient_update.getText().toString().trim();
            measurement = measurement_update.getText().toString().trim();
            repository.updateData(id, title, ingredient, measurement);
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("chef") && getIntent().hasExtra("ingredients")){
            //Om det finns information att visa, hämtar vi den från intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            ingredient = getIntent().getStringExtra("chef");
            measurement = getIntent().getStringExtra("ingredients");

            //Visar data som går att uppdatera.
            title_update.setText(title);
            ingredient_update.setText(ingredient);
            measurement_update.setText(measurement);
        }else{
            Toast.makeText(UpdateActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
        }
    }
    //Ta bort ett recept med en säkerhetsvarning
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete_item) + title + "?");
        builder.setMessage(getString(R.string.delete_item_message) + title + "?");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Repository repository = new RepositoryImplementation(UpdateActivity.this);
                repository.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}