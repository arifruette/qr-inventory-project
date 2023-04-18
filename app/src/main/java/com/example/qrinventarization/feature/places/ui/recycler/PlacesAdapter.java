package com.example.qrinventarization.feature.places.ui.recycler;

import static java.lang.Math.max;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrinventarization.databinding.PlacesRecycleBinding;
import com.example.qrinventarization.domain.model.places.Place;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesViewHolder> {
    private List<Place> places = new ArrayList<>();
    private PlacesListener listener;

    public PlacesAdapter(PlacesListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PlacesRecycleBinding binding = PlacesRecycleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PlacesViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        holder.bind(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setPlaces(List<Place> places){
        int count = getItemCount();
        this.places = places;
        notifyItemRangeChanged(0, max(count, getItemCount()));
    }

}
