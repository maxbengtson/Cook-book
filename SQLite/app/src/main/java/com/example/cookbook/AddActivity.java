package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlite.R;

public class AddActivity extends AppCompatActivity {

    EditText title, ingredient, measurement;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        title = findViewById(R.id.recipe_add);
        ingredient = findViewById(R.id.ingredient_add);
        measurement = findViewById(R.id.measurement_add);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(view -> {
            MyDatabaseHelper myDb = new MyDatabaseHelper(AddActivity.this);
            myDb.addIem(title.getText().toString().trim(),
                    ingredient.getText().toString().trim(),
                    Integer.parseInt(measurement.getText().toString().trim()));
        });
    }
}