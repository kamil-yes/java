package com.kamil.java;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritesFragment extends Fragment {
    private RecyclerView list;
    private FavoritesListAdapter adapter;

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
        loadFavorites();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void loadFavorites() {
        DBAdapterFavorite dbAdapterFavorite = new DBAdapterFavorite(requireContext()).open();
        List<Favorite> favorites = dbAdapterFavorite.getFavorites(new User(1, ""));
        dbAdapterFavorite.close();

        adapter = new FavoritesListAdapter(requireContext(), favorites);
        list.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }
}
