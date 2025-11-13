package com.kamil.java;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddAircraft extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_aircraft);

        EditText name = findViewById(R.id.name);
        EditText model = findViewById(R.id.model);
        EditText creation_year = findViewById(R.id.creation_year);
        EditText inspection_year = findViewById(R.id.inspection_year);

        Button save = findViewById(R.id.save);
        save.setOnClickListener(v -> {
            String name_value = name.getText().toString();
            String model_value = model.getText().toString();
            String creation_year_value = creation_year.getText().toString();
            String inspection_year_value = inspection_year.getText().toString();

            if(name_value.isEmpty()) Toast.makeText(this, "Please, fill the name.", Toast.LENGTH_SHORT).show();
            else if(model_value.isEmpty()) Toast.makeText(this, "Please, fill the model.", Toast.LENGTH_SHORT).show();
            else if(creation_year_value.isEmpty()) Toast.makeText(this, "Please, fill the creation year.", Toast.LENGTH_SHORT).show();
            else if(inspection_year_value.isEmpty()) Toast.makeText(this, "Please, fill the inspection year.", Toast.LENGTH_SHORT).show();
            else {
                Aircraft aircraft = new Aircraft(0, name_value, model_value, Integer.parseInt(creation_year_value), Integer.parseInt(inspection_year_value));
                DBAdapter dbAdapter = new DBAdapter(this).open();
                dbAdapter.insert(aircraft);
                this.finish();
            }
        });
    }
}