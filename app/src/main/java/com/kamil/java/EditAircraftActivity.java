package com.kamil.java;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class EditAircraftActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_aircraft);

        Bundle args = getIntent().getExtras();
        assert args != null;

        DBAdapterAircraft dbAdapterAircraft = new DBAdapterAircraft(this).open();
        Aircraft aircraft = dbAdapterAircraft.getAircraft(args.getLong("id"));

        EditText name = findViewById(R.id.name);
        EditText model = findViewById(R.id.model);
        EditText creation_year = findViewById(R.id.creation_year);
        EditText inspection_year = findViewById(R.id.inspection_year);
        EditText price = findViewById(R.id.price);
        EditText description = findViewById(R.id.description);

        name.setText(aircraft.name);
        model.setText(aircraft.model);
        creation_year.setText(String.valueOf(aircraft.yearOfCreation));
        inspection_year.setText(String.valueOf(aircraft.yearOfInspection));
        price.setText(String.valueOf(aircraft.price));
        description.setText(String.valueOf(aircraft.description));

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
                aircraft.name = name_value;
                aircraft.model = model_value;
                aircraft.yearOfCreation = Integer.parseInt(creation_year_value);
                aircraft.yearOfInspection = Integer.parseInt(inspection_year_value);
                aircraft.price = Integer.parseInt(price_value);
                aircraft.description = description_value;
                dbAdapterAircraft.update(aircraft);

                this.finish();
            }
        });
    }
}