package com.example.qrinventarization.feature.places.ui.recycler;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qrinventarization.databinding.PlacesRecycleBinding;
import com.example.qrinventarization.domain.model.places.Place;

public class PlacesViewHolder extends RecyclerView.ViewHolder {

    PlacesListener listener;
    PlacesRecycleBinding binding;

    public PlacesViewHolder(PlacesRecycleBinding binding, PlacesListener listener){
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public void bind(Place place){
        binding.placeName.setText(place.getText().toString().replace(".0", ""));
        //TODO set checkbox
        binding.checkbox1.setChecked(place.isChecked());
    }
}
