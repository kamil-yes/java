package com.kamil.java;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AddAircraftActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_aircraft);

        EditText name = findViewById(R.id.name);
        EditText model = findViewById(R.id.model);
        EditText creation_year = findViewById(R.id.creation_year);
        EditText inspection_year = findViewById(R.id.inspection_year);
        EditText price = findViewById(R.id.price);
        EditText description = findViewById(R.id.description);

        Button save = findViewById(R.id.save);
        save.setOnClickListener(v -> {
            String name_value = name.getText().toString();
            String model_value = model.getText().toString();
            String creation_year_value = creation_year.getText().toString();
            String inspection_year_value = inspection_year.getText().toString();
            String price_value = price.getText().toString();
            String description_value = description.getText().toString();

            if(name_value.isEmpty()) Toast.makeText(this, "Please, fill the name.", Toast.LENGTH_SHORT).show();
            else if(model_value.isEmpty()) Toast.makeText(this, "Please, fill the model.", Toast.LENGTH_SHORT).show();
            else if(creation_year_value.isEmpty()) Toast.makeText(this, "Please, fill the creation year.", Toast.LENGTH_SHORT).show();
            else if(inspection_year_value.isEmpty()) Toast.makeText(this, "Please, fill the inspection year.", Toast.LENGTH_SHORT).show();
            else if(price_value.isEmpty()) Toast.makeText(this, "Please, fill the price.", Toast.LENGTH_SHORT).show();
            else if(description_value.isEmpty()) Toast.makeText(this, "Please, fill the description.", Toast.LENGTH_SHORT).show();
            else {
                Aircraft aircraft = new Aircraft(0, name_value, model_value, Integer.parseInt(creation_year_value), Integer.parseInt(inspection_year_value), Integer.parseInt(price_value), description_value);
                DBAdapterAircraft dbAdapterAircraft = new DBAdapterAircraft(this).open();
                dbAdapterAircraft.insert(aircraft);
                dbAdapterAircraft.close();
                this.finish();
            }
        });
    }
}