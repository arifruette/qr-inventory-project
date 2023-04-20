package com.example.qrinventarization.feature.inventarization.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qrinventarization.R;
import com.example.qrinventarization.databinding.FragmentInventarizationBinding;
import com.example.qrinventarization.domain.model.items.Item;
import com.example.qrinventarization.feature.inventarization.presentation.InventarizationViewModel;
import com.example.qrinventarization.feature.object.presentation.ObjectViewModel;
import com.example.qrinventarization.feature.object.ui.ObjectFragment;
import com.example.qrinventarization.feature.object.ui.ObjectFragmentArgs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class InventarizationFragment extends Fragment {

    private FragmentInventarizationBinding binding;
    private InventarizationFragmentArgs args;
    private HashMap<String, ArrayList<Item>> inventarizationItems;
    private InventarizationViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInventarizationBinding.inflate(inflater);
        viewModel = new ViewModelProvider(this).get(InventarizationViewModel.class);
        args = InventarizationFragmentArgs.fromBundle(requireArguments());
        if(savedInstanceState == null) {
            viewModel.load();
            viewModel.items.observe(getViewLifecycleOwner(), this::renderItems);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void renderItems(List<Item> itemsList){
        inventarizationItems = new HashMap<>();
        for(int i =0;i < itemsList.size();i++){
            System.out.println(itemsList.get(i).getPlace());
            System.out.println(itemsList.get(i).getName());
            if(itemsList.get(i).getPlace() != null){
                String place = itemsList.get(i).getPlace().replace(".0", "");
                if(Arrays.asList(args.getLocations().split(" ")).contains(place)){
                    ArrayList<Item> final_list = inventarizationItems.get(place);
                    final_list.add(itemsList.get(i));
                    inventarizationItems.put(place, final_list);
                }
            }
        }
        System.out.println(inventarizationItems.toString());
    }
}