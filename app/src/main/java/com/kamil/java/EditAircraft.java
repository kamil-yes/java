package com.kamil.java;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditAircraft extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_aircraft);

        Bundle args = getIntent().getExtras();
        assert args != null;
        long id = args.getLong("id");

        DBAdapter dbAdapter = new DBAdapter(this).open();
        Aircraft aircraft = dbAdapter.getAircraft(id);

        EditText name = findViewById(R.id.name);
        EditText model = findViewById(R.id.model);
        EditText creation_year = findViewById(R.id.creation_year);
        EditText inspection_year = findViewById(R.id.inspection_year);

        name.setText(aircraft.name);
        model.setText(aircraft.model);
        creation_year.setText(String.valueOf(aircraft.yearOfCreation));
        inspection_year.setText(String.valueOf(aircraft.yearOfInspection));

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            dbAdapter.delete(id);
            this.finish();
        });

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
                aircraft.name = name_value;
                aircraft.model = model_value;
                aircraft.yearOfCreation = Integer.parseInt(creation_year_value);
                aircraft.yearOfInspection = Integer.parseInt(inspection_year_value);
                dbAdapter.update(aircraft);
                this.finish();
            }
        });
    }
}