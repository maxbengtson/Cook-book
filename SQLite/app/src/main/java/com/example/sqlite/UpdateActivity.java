package com.example.sqlite;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    EditText recipe_update, ingredient_update, chef_update;
    Button update_button, delete_button;

    String id, recipe, ingredient, chef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        recipe_update = findViewById(R.id.recipe_update);
        ingredient_update = findViewById(R.id.ingredient_update);
        chef_update = findViewById(R.id.measurement_update);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();


        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setTitle(recipe);
        }

        update_button.setOnClickListener(view -> {
            Repository repository = new RepositoryImplementation(UpdateActivity.this);
            recipe = recipe_update.getText().toString().trim();
            chef = chef_update.getText().toString().trim();
            ingredient = ingredient_update.getText().toString().trim();
            repository.updateData(id, recipe, ingredient, chef);
        });
        delete_button.setOnClickListener(v -> confirmDialog());
    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("recipe") && getIntent().hasExtra("chef") && getIntent().hasExtra("ingredients")){
            //Om det finns information att visa, hämtar vi den från intent
            id = getIntent().getStringExtra("id");
            recipe = getIntent().getStringExtra("recipe");
            chef = getIntent().getStringExtra("chef");
            ingredient = getIntent().getStringExtra("ingredients");

            //Visar data som går att uppdatera.
            recipe_update.setText(recipe);
            ingredient_update.setText(ingredient);
            chef_update.setText(chef);
        }else{
            Toast.makeText(UpdateActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
        }
    }
    //Ta bort ett recept med en säkerhetsvarning
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete_item) + recipe + "?");
        builder.setMessage(getString(R.string.delete_item_message) + recipe + "?");
        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            Repository repository = new RepositoryImplementation(UpdateActivity.this);
            repository.deleteOneRow(id);
            finish();
        });
        builder.setNegativeButton(R.string.no, (dialog, which) -> {

        });
        builder.create().show();
    }
}