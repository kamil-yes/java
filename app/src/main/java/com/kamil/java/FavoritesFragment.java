package com.kamil.java;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritesFragment extends Fragment {
    private RecyclerView list;
    private FavoritesListAdapter adapter;
    private TextView emptyPlaceholder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = view.findViewById(R.id.list);
        emptyPlaceholder = view.findViewById(R.id.emptyView);
    }

    @Override
    public void onResume() {
        super.onResume();

        DBAdapterFavorite dbAdapterFavorite = new DBAdapterFavorite(requireContext()).open();
        List<Favorite> favorites = dbAdapterFavorite.getFavorites(MainActivity.user);
        dbAdapterFavorite.close();

        if (favorites.isEmpty()) {
            emptyPlaceholder.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        } else {
            emptyPlaceholder.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);

            adapter = new FavoritesListAdapter(requireContext(), favorites, this);
            list.setAdapter(adapter);
        }
    }
}
