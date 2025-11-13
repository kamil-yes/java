package com.kamil.java;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DBAdapter dbAdapter = new DBAdapter(this).open();
        List<Aircraft> aircrafts = dbAdapter.getAircrafts();
        dbAdapter.close();

        RecyclerView list = findViewById(R.id.list);
        adapter = new ListAdapter(this, aircrafts);
        list.setAdapter(adapter);

        Button addAircraft = findViewById(R.id.add);
        addAircraft.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAircraft.class);
            startActivity(intent);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    protected void onResume() {
        super.onResume();
        DBAdapter dbAdapter = new DBAdapter(this).open();
        adapter.list = dbAdapter.getAircrafts();
        adapter.notifyDataSetChanged();
        dbAdapter.close();
    }
}