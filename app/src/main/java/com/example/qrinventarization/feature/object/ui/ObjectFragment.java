package com.example.qrinventarization.feature.object.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.qrinventarization.databinding.FragmentObjectBinding;
import com.example.qrinventarization.domain.model.Item;
import com.example.qrinventarization.feature.items.presentation.ItemsStatus;
import com.example.qrinventarization.feature.object.presentation.ObjectStatus;
import com.example.qrinventarization.feature.object.presentation.ObjectViewModel;

import java.util.List;

public class ObjectFragment extends Fragment {

    private ObjectViewModel viewModel;

    private FragmentObjectBinding binding;
    private ObjectFragmentArgs args;

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

        binding.imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Navigation.findNavController(binding.getRoot()).navigateUp();
            }
        });

        viewModel.status.observe(getViewLifecycleOwner(), this::renderStatus);
        viewModel.item.observe(getViewLifecycleOwner(), this::renderItem);
        if(savedInstanceState==null) viewModel.load(args.getId());
    }

    private void renderStatus(ObjectStatus status){
        switch (status){

            case LOADING:
                binding.objectPlace.setVisibility(View.INVISIBLE);
                binding.objectNumber.setVisibility(View.INVISIBLE);
                binding.objectCard.setVisibility(View.INVISIBLE);
                binding.editObject.setVisibility(View.INVISIBLE);
                binding.historyObject.setVisibility(View.INVISIBLE);
                binding.objectName.setVisibility(View.INVISIBLE);

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
                binding.historyObject.setVisibility(View.VISIBLE);
                binding.objectName.setVisibility(View.VISIBLE);

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
        }
    }

    private void renderItem(Item item){
        binding.objectName.setText(item.getName());
        binding.objectNumber.setText(item.getSerial_number());
        binding.objectPlace.setText(item.getPlace());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}