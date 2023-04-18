package com.example.qrinventarization.feature.places.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrinventarization.databinding.FragmentPlacesBinding;
import com.example.qrinventarization.domain.model.places.Place;
import com.example.qrinventarization.feature.places.presentation.PlacesViewModel;
import com.example.qrinventarization.feature.places.ui.recycler.PlacesAdapter;

import java.util.List;

public class PlacesFragment extends Fragment {

    private List<Place> places;

    private PlacesViewModel viewModel;
    private PlacesAdapter adapter;

    private FragmentPlacesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(PlacesViewModel.class);
        binding = FragmentPlacesBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new PlacesAdapter(this::changeById);

        binding.placesRecycler.setAdapter(adapter);


        viewModel.places.observe(getViewLifecycleOwner(), this::setPlaces);

        if(savedInstanceState == null) viewModel.load();
    }

    public void setPlaces(List<Place> lst){
        this.places = lst;
        adapter.setPlaces(lst);
    }

    public void changeById(long id){
        for(int i = 0;i < places.size();i++){
            if(places.get(i).getId() == id){
                places.get(i).setChecked(true);
            }
        }
    }
}