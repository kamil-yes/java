package com.kamil.java;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAircraftsFragment extends Fragment {
    private AircraftsListAdapter adapter;
    private RecyclerView list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_aircrafts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = view.findViewById(R.id.list);
        Button addAircraft = view.findViewById(R.id.add);

        addAircraft.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddAircraftActivity.class);
            startActivity(intent);
        });
        loadAircrafts();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void loadAircrafts() {
        DBAdapterAircraft dbAdapterAircraft = new DBAdapterAircraft(requireContext()).open();
        List<Aircraft> aircrafts = dbAdapterAircraft.getAircrafts();
        dbAdapterAircraft.close();

        adapter = new AircraftsListAdapter(requireContext(), aircrafts);
        list.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAircrafts();
    }
}
