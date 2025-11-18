package com.kamil.java;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    public List<Favorite> list;

    public FavoritesListAdapter(Context context, List<Favorite> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_favorites_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onBindViewHolder(FavoritesListAdapter.ViewHolder holder, int position) {
        Favorite favorite = list.get(position);
        holder.name.setText(favorite.aircraft.name);
        holder.description.setText(favorite.aircraft.description);
        holder.creationDate.setText(favorite.creationDate);
        holder.is_favorite.setChecked(true);

        User user = new User(1, "");

        holder.is_favorite.setOnCheckedChangeListener((checkbox, isChecked) -> {
            DBAdapterFavorite adapter = new DBAdapterFavorite(context).open();
            if (!isChecked) {
                adapter.delete(user, favorite.aircraft);
                list = adapter.getFavorites(user);
                notifyDataSetChanged();
            }
            adapter.close();
        });
    }

    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;
        public TextView creationDate;
        public CheckBox is_favorite;
        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
            creationDate = view.findViewById(R.id.creation_date);
            is_favorite = view.findViewById(R.id.is_favorite);
        }
    }
}
