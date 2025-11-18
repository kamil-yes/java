package com.kamil.java;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShowAircraftActivity extends AppCompatActivity {
    private TextView name;
    private TextView model;
    private TextView creationYear;
    private TextView inspectionYear;
    private TextView price;
    private TextView description;
    private DBAdapterAircraft dbAdapterAircraft;
    private Aircraft aircraft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_aircraft);

        Bundle args = getIntent().getExtras();
        assert args != null;

        dbAdapterAircraft = new DBAdapterAircraft(this).open();
        aircraft = dbAdapterAircraft.getAircraft(args.getLong("id"));

        name = findViewById(R.id.name_value);
        model = findViewById(R.id.model_value);
        creationYear = findViewById(R.id.creation_year_value);
        inspectionYear = findViewById(R.id.inspection_year_value);
        price = findViewById(R.id.price_value);
        description = findViewById(R.id.description_value);

        Button editButton = findViewById(R.id.edit_button);
        Button deleteButton = findViewById(R.id.delete_button);

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditAircraftActivity.class);
            intent.putExtra("id", aircraft.id);
            startActivity(intent);
        });

        deleteButton.setOnClickListener(v -> {
            dbAdapterAircraft.delete(aircraft.id);
            this.finish();
        });
    }

    public void onResume() {
        super.onResume();
        aircraft = dbAdapterAircraft.getAircraft(aircraft.id);
        name.setText(aircraft.name);
        model.setText(aircraft.model);
        creationYear.setText(String.valueOf(aircraft.yearOfCreation));
        inspectionYear.setText(String.valueOf(aircraft.yearOfInspection));
        price.setText(String.valueOf(aircraft.price));
        description.setText(aircraft.description);
    }
}