package com.example.qrinventarization.feature.object.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.qrinventarization.databinding.FragmentObjectBinding;
import com.example.qrinventarization.domain.model.items.Item;
import com.example.qrinventarization.domain.model.places.Place;
import com.example.qrinventarization.feature.object.presentation.ObjectStatus;
import com.example.qrinventarization.feature.object.presentation.ObjectViewModel;

import java.util.ArrayList;
import java.util.List;

public class ObjectFragment extends Fragment {

    private ObjectViewModel viewModel;

    private FragmentObjectBinding binding;
    private ObjectFragmentArgs args;
    private ArrayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        args = ObjectFragmentArgs.fromBundle(requireArguments());
        viewModel = new ViewModelProvider(this).get(ObjectViewModel.class);
        binding = FragmentObjectBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imageButton.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigateUp());

        viewModel.status.observe(getViewLifecycleOwner(), this::renderStatus);
        viewModel.item.observe(getViewLifecycleOwner(), this::renderItem);
        if(savedInstanceState==null) viewModel.load(args.getId());

        binding.editObject.setOnClickListener(view1 -> {
            binding.objectName.setEnabled(true);
            binding.objectNumber.setEnabled(true);
            viewModel.status.observe(getViewLifecycleOwner(), ObjectFragment.this::renderStatus);
            viewModel.places();
            viewModel.places.observe(getViewLifecycleOwner(), ObjectFragment.this::renderPlaces);
        });

//        binding.saveChanges.setOnClickListener(v -> {
//            viewModel.update(args.getId(), new Item(args.getId(), binding.objectName.getText().toString(), binding.objectNumber.getText().toString(), binding.spinner.getSelectedItem().toString()));
//            viewModel.status.observe(getViewLifecycleOwner(), ObjectFragment.this::renderStatus);
//            System.out.println(binding.spinner.getSelectedItem());
//        });
    }

    private void renderStatus(ObjectStatus status){
        switch (status){

            case LOADING:
            case LOADING_PLACES:
                binding.objectPlace.setVisibility(View.INVISIBLE);
                binding.objectNumber.setVisibility(View.INVISIBLE);
                binding.objectCard.setVisibility(View.INVISIBLE);
                binding.editObject.setVisibility(View.INVISIBLE);
                binding.historyObject.setVisibility(View.INVISIBLE);
                binding.objectName.setVisibility(View.INVISIBLE);
                binding.placesError.setVisibility(View.INVISIBLE);

                binding.objectProgress.setVisibility(View.VISIBLE);

                binding.nameHint.setVisibility(View.INVISIBLE);
                binding.numberHint.setVisibility(View.INVISIBLE);
                binding.placeHint.setVisibility(View.INVISIBLE);
                binding.objectError.setVisibility(View.INVISIBLE);

                break;
            case LOADED:

                binding.objectPlace.setVisibility(View.VISIBLE);
                binding.objectNumber.setVisibility(View.VISIBLE);
                binding.objectCard.setVisibility(View.VISIBLE);
                binding.editObject.setVisibility(View.VISIBLE);
                binding.placesError.setVisibility(View.INVISIBLE);
                binding.historyObject.setVisibility(View.VISIBLE);
                binding.objectName.setVisibility(View.VISIBLE);
                binding.objectName.setEnabled(false);
                binding.objectPlace.setEnabled(false);
                binding.objectNumber.setEnabled(false);
                binding.saveChanges.setVisibility(View.INVISIBLE);

                binding.objectProgress.setVisibility(View.INVISIBLE);

                binding.nameHint.setVisibility(View.VISIBLE);
                binding.numberHint.setVisibility(View.VISIBLE);
                binding.placeHint.setVisibility(View.VISIBLE);
                binding.objectError.setVisibility(View.INVISIBLE);

                break;

            case FAILURE:

                binding.objectPlace.setVisibility(View.INVISIBLE);
                binding.objectNumber.setVisibility(View.INVISIBLE);
                binding.objectCard.setVisibility(View.INVISIBLE);
                binding.placesError.setVisibility(View.INVISIBLE);
                binding.editObject.setVisibility(View.INVISIBLE);
                binding.historyObject.setVisibility(View.INVISIBLE);
                binding.objectName.setVisibility(View.INVISIBLE);

                binding.objectProgress.setVisibility(View.INVISIBLE);

                binding.nameHint.setVisibility(View.INVISIBLE);
                binding.numberHint.setVisibility(View.INVISIBLE);
                binding.placeHint.setVisibility(View.INVISIBLE);

                binding.objectError.setVisibility(View.VISIBLE);
                break;

            case DELETED:
                Navigation.findNavController(binding.getRoot()).navigateUp();
                break;

            case LOADED_PLACES:
                binding.objectPlace.setVisibility(View.INVISIBLE);
                binding.spinner.setVisibility(View.VISIBLE);
                binding.objectNumber.setVisibility(View.VISIBLE);
                binding.objectCard.setVisibility(View.VISIBLE);
                binding.editObject.setVisibility(View.INVISIBLE);
                binding.placesError.setVisibility(View.INVISIBLE);
                binding.saveChanges.setVisibility(View.VISIBLE);

                binding.objectName.setEnabled(true);
                binding.objectNumber.setEnabled(true);

                binding.historyObject.setVisibility(View.INVISIBLE);
                binding.objectName.setVisibility(View.VISIBLE);

                binding.objectProgress.setVisibility(View.INVISIBLE);

                binding.nameHint.setVisibility(View.VISIBLE);
                binding.numberHint.setVisibility(View.VISIBLE);
                binding.placeHint.setVisibility(View.VISIBLE);
                binding.objectError.setVisibility(View.INVISIBLE);

                break;

            case FAILURE_PLACES:
                binding.objectPlace.setVisibility(View.INVISIBLE);
                binding.objectNumber.setVisibility(View.INVISIBLE);
                binding.objectCard.setVisibility(View.INVISIBLE);
                binding.editObject.setVisibility(View.INVISIBLE);
                binding.historyObject.setVisibility(View.INVISIBLE);


                binding.objectProgress.setVisibility(View.INVISIBLE);

                binding.nameHint.setVisibility(View.INVISIBLE);
                binding.numberHint.setVisibility(View.INVISIBLE);
                binding.placeHint.setVisibility(View.INVISIBLE);

                binding.placesError.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void renderItem(Item item){
        binding.objectName.setText(item.getName());
        binding.objectNumber.setText(item.getSerial_number());
        System.out.println(item.getPlace());
        if(item.getPlace() != null){
            binding.objectPlace.setText(item.getPlace());
        }else{
            binding.objectPlace.setText("Не указано");
        }

    }

    private void renderPlaces(List<Place> places){
        List<String> lst = new ArrayList<>();
        for(int i = 0;i < places.size();i++){

                lst.add(places.get(i).getText().toString().replace(".0", ""));

        }
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lst);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}