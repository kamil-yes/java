package com.kamil.java;

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

public class AircraftsListAdapter extends RecyclerView.Adapter<AircraftsListAdapter.ViewHolder> {
    private Context context;
    private final LayoutInflater inflater;
    public List<Aircraft> list;

    public AircraftsListAdapter(Context context, List<Aircraft> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.list_aircrafts_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(AircraftsListAdapter.ViewHolder holder, int position) {
        Aircraft aircraft = list.get(position);
        holder.name.setText(aircraft.name);
        holder.description.setText(aircraft.description);
        holder.price.setText(String.valueOf(aircraft.price));

        User user = new User(1, "");
        DBAdapterFavorite dbAdapterFavorite = new DBAdapterFavorite(context).open();
        holder.is_favorite.setChecked(dbAdapterFavorite.isFavorite(user, aircraft));
        dbAdapterFavorite.close();

        holder.is_favorite.setOnCheckedChangeListener((checkbox, isChecked) -> {
            DBAdapterFavorite adapter = new DBAdapterFavorite(context).open();

            if (isChecked) {
                adapter.insert(user, aircraft);
            } else {
                adapter.delete(user, aircraft);
            }

            adapter.close();
        });
        holder.itemView.setOnClickListener(v -> {
            Context context = inflater.getContext();
            Intent intent = new Intent(context, EditAircraftActivity.class);
            intent.putExtra("id", aircraft.id);
            context.startActivity(intent);
        });
    }

    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;
        public TextView price;
        public CheckBox is_favorite;
        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
            is_favorite = view.findViewById(R.id.is_favorite);
            price = view.findViewById(R.id.price);
        }
    }
}
