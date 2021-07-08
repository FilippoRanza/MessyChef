package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class RecipeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        String[] names  = {"A", "B", "C", "D", "E", "F"};

        ArrayAdapter<String> content = new ArrayAdapter<>(this, R.layout.list_element, names);

        AdapterView<ArrayAdapter<String>> adapter = findViewById(R.id.recipe_list);
        adapter.setAdapter(content);

    }
}