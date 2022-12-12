package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText recipe, ingredient, chef; // States
    Button add_button;

    @Override // Definera vad vi hittar var i vår addActivity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        recipe = findViewById(R.id.recipe_add);
        ingredient = findViewById(R.id.ingredient_add);
        chef = findViewById(R.id.measurement_add);
        add_button = findViewById(R.id.add_button);
            //När vi klickar på "Add" tar vi in information och sparar dom i våra states
        add_button.setOnClickListener(view -> {
            Repository repository = new RepositoryImplementation(AddActivity.this);
            repository.addIem(recipe.getText().toString().trim(),
                    ingredient.getText().toString().trim(),
                    chef.getText().toString().trim());
            recipe.setText("");
            ingredient.setText("");
            chef.setText("");
        });
    }
}