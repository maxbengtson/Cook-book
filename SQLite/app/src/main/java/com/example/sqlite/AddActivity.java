package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText title, author_input, pages_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        title = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(view -> {
            MyDatabaseHelper myDb = new MyDatabaseHelper(AddActivity.this);
            myDb.addBook(title.getText().toString().trim(),
                    author_input.getText().toString().trim(),
                    Integer.parseInt(pages_input.getText().toString().trim()));
        });
    }
}