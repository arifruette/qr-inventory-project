package com.example.qrinventarization.feature.inventarization.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrinventarization.databinding.FragmentInventarizationBinding;
import com.example.qrinventarization.domain.model.items.Item;
import com.example.qrinventarization.feature.inventarization.presentation.InventarizationStatus;
import com.example.qrinventarization.feature.inventarization.presentation.InventarizationViewModel;
import com.example.qrinventarization.feature.inventarization.ui.recycler.InventarizationAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class InventarizationFragment extends Fragment {

    private FragmentInventarizationBinding binding;
    private InventarizationFragmentArgs args;
    private HashMap<String, ArrayList<Item>> inventarizationItems;
    private InventarizationViewModel viewModel;
    private ArrayList<String> locations;
    private ArrayAdapter<String> adapter;
    private InventarizationAdapter adapter_objects;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInventarizationBinding.inflate(inflater);
        viewModel = new ViewModelProvider(this).get(InventarizationViewModel.class);
        args = InventarizationFragmentArgs.fromBundle(requireArguments());
        adapter_objects = new InventarizationAdapter();

        if(savedInstanceState == null) {
            viewModel.load();

        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.status.observe(getViewLifecycleOwner(), InventarizationFragment.this::renderStatus);
        viewModel.items.observe(getViewLifecycleOwner(), this::renderItems);
        binding.locationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Выберите помещение:    ")){

                }else{
                    System.out.println(((List<Item>) inventarizationItems.get(parent.getItemAtPosition(position))));
                    adapter_objects.setItems((List<Item>) inventarizationItems.get(parent.getItemAtPosition(position)));
                    binding.rV.setAdapter(adapter_objects);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void renderItems(List<Item> itemsList){
        inventarizationItems = new HashMap<>();

        for(int i =0;i < itemsList.size();i++){

            if(!itemsList.get(i).getPlace().equals("None")){
                String place = itemsList.get(i).getPlace().replace(".0", "");
                if(Arrays.asList(args.getLocations().split(" ")).contains(place)){
                    if(inventarizationItems.containsKey(place)){
                        ArrayList<Item> final_list = inventarizationItems.get(place);
                        final_list.add(itemsList.get(i));
                        inventarizationItems.put(place, final_list);
                    }else{
                        ArrayList<Item> final_list = new ArrayList<>();
                        final_list.add(itemsList.get(i));
                        inventarizationItems.put(place, final_list);
                    }
                }
            }
        }
        locations = new ArrayList<>();
        locations.addAll(inventarizationItems.keySet());

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, locations);
        binding.locationsSpinner.setPrompt("Выберите помещение");
        binding.locationsSpinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //System.out.println(inventarizationItems.toString());
    }

    private void renderStatus(InventarizationStatus status){
        switch (status){

            case LOADING:
                binding.locationsSpinner.setVisibility(View.INVISIBLE);
                binding.rV.setVisibility(View.INVISIBLE);
                binding.scan.setVisibility(View.INVISIBLE);
                binding.locationsSpinner.setVisibility(View.INVISIBLE);
                binding.errorInvent.setVisibility(View.INVISIBLE);

                binding.contentLoading.setVisibility(View.VISIBLE);
                break;
            case LOADED:
                binding.locationsSpinner.setVisibility(View.VISIBLE);
                binding.rV.setVisibility(View.VISIBLE);
                binding.scan.setVisibility(View.VISIBLE);
                binding.locationsSpinner.setVisibility(View.VISIBLE);

                binding.errorInvent.setVisibility(View.INVISIBLE);

                binding.contentLoading.setVisibility(View.INVISIBLE);

                break;

            case FAILURE:
                binding.locationsSpinner.setVisibility(View.INVISIBLE);
                binding.rV.setVisibility(View.INVISIBLE);
                binding.scan.setVisibility(View.INVISIBLE);
                binding.locationsSpinner.setVisibility(View.INVISIBLE);

                binding.errorInvent.setVisibility(View.VISIBLE);

                binding.contentLoading.setVisibility(View.INVISIBLE);

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}