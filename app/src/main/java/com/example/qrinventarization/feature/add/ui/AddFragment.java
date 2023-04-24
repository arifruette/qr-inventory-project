package com.example.qrinventarization.feature.add.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrinventarization.databinding.FragmentAddBinding;
import com.example.qrinventarization.domain.model.places.Place;
import com.example.qrinventarization.feature.add.presentation.AddStatus;
import com.example.qrinventarization.feature.add.presentation.AddViewModel;
import com.example.qrinventarization.feature.items.presentation.ItemsStatus;

import java.util.ArrayList;
import java.util.List;

public class AddFragment extends Fragment {
    private FragmentAddBinding binding;
    private AddViewModel viewModel;
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddViewModel.class);
        binding = FragmentAddBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.status.observe(getViewLifecycleOwner(), this::renderStatus);
        viewModel.places.observe(getViewLifecycleOwner(), this::renderPlaces);

        viewModel.get_places();
    }

    private void renderStatus(AddStatus status){
        switch (status){

            case LOADING:
                binding.addProgress.setVisibility(View.VISIBLE);

                binding.nameObject.setVisibility(View.INVISIBLE);
                binding.serialNumberObj.setVisibility(View.INVISIBLE);
                binding.textView2.setVisibility(View.INVISIBLE);
                binding.addObjBtn.setVisibility(View.INVISIBLE);
                binding.spinnerAdd.setVisibility(View.INVISIBLE);

                binding.addError.setVisibility(View.INVISIBLE);
                break;
            case LOADED:
                binding.addProgress.setVisibility(View.INVISIBLE);

                binding.nameObject.setVisibility(View.VISIBLE);
                binding.serialNumberObj.setVisibility(View.VISIBLE);
                binding.textView2.setVisibility(View.VISIBLE);
                binding.spinnerAdd.setVisibility(View.VISIBLE);
                binding.addObjBtn.setVisibility(View.VISIBLE);

                binding.addError.setVisibility(View.INVISIBLE);
                break;

            case FAILURE:

                binding.addProgress.setVisibility(View.INVISIBLE);

                binding.nameObject.setVisibility(View.INVISIBLE);
                binding.serialNumberObj.setVisibility(View.INVISIBLE);
                binding.textView2.setVisibility(View.INVISIBLE);
                binding.spinnerAdd.setVisibility(View.INVISIBLE);
                binding.addObjBtn.setVisibility(View.INVISIBLE);

                binding.addError.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void renderPlaces(List<Place> places){
        ArrayList<String> itog = new ArrayList<>();
        for(Place s: places){
            itog.add(s.getText().toString().replace(".0", ""));
        }
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, itog);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAdd.setAdapter(adapter);
        binding.spinnerAdd.setPrompt("Выберите помещение");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
