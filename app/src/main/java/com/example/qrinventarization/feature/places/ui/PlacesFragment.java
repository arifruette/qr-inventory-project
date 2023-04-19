package com.example.qrinventarization.feature.places.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrinventarization.databinding.FragmentPlacesBinding;
import com.example.qrinventarization.domain.model.places.Place;
import com.example.qrinventarization.feature.items.presentation.ItemsStatus;
import com.example.qrinventarization.feature.places.presentation.PlacesStatus;
import com.example.qrinventarization.feature.places.presentation.PlacesViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlacesFragment extends Fragment {

    private ArrayAdapter<String> locations_adapter;

    private PlacesViewModel viewModel;

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

        viewModel.status.observe(getViewLifecycleOwner(), this::renderStatus);
        viewModel.places.observe(getViewLifecycleOwner(), this::setAdapter);

        if(savedInstanceState == null) viewModel.load();

        binding.startInventarization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cntChoice = binding.listView.getCount();

                ArrayList<String> checked = new ArrayList<>();

                SparseBooleanArray sparseBooleanArray = binding.listView.getCheckedItemPositions();

                for (int i = 0; i < cntChoice; i++) {

                    if (sparseBooleanArray.get(i)) {
                        String post = binding.listView.getItemAtPosition(i).toString();
                        checked.add(post);
                    }
                }
                SharedPreferences sp = getActivity().getSharedPreferences("mysett", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("places", checked.toString());
                edit.apply();


                System.out.println(checked.toString());
            }
        });
    }

    private void renderStatus(PlacesStatus status){
        switch (status){

            case LOADING:
                binding.listView.setVisibility(View.INVISIBLE);
                binding.errorPlaces.setVisibility(View.INVISIBLE);
                binding.placesProgress.setVisibility(View.VISIBLE);


                break;
            case LOADED:
                binding.listView.setVisibility(View.VISIBLE);
                binding.errorPlaces.setVisibility(View.INVISIBLE);
                binding.placesProgress.setVisibility(View.INVISIBLE);
                break;

            case FAILURE:
                binding.listView.setVisibility(View.INVISIBLE);
                binding.errorPlaces.setVisibility(View.VISIBLE);
                binding.placesProgress.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void setAdapter(@NonNull List<Place> lst){
        ArrayList<String> places = new ArrayList<>();
        for(int i =0;i<lst.size();i++){
            places.add(lst.get(i).getText().toString().replace(".0", ""));
        }
        locations_adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_multiple_choice, places);

        binding.listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        binding.listView.setAdapter(locations_adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}