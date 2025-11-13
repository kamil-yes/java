package com.kamil.java;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    public List<Aircraft> list;

    public ListAdapter(Context context, List<Aircraft> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        Aircraft aircraft = list.get(position);
        holder.name.setText(aircraft.name);
        holder.itemView.setOnClickListener(v -> {
            Context context = inflater.getContext();
            Intent intent = new Intent(context, EditAircraft.class);
            intent.putExtra("id", aircraft.id);
            context.startActivity(intent);
        });
    }

    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
        }
    }
}
