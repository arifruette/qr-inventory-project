package com.example.qrinventarization.feature.inventarization.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qrinventarization.R;
import com.example.qrinventarization.databinding.FragmentInventarizationBinding;
import com.example.qrinventarization.feature.object.ui.ObjectFragmentArgs;


public class InventarizationFragment extends Fragment {

    private FragmentInventarizationBinding binding;
    private InventarizationFragmentArgs args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInventarizationBinding.inflate(inflater);
        args = InventarizationFragmentArgs.fromBundle(requireArguments());
        System.out.println(args.getLocations());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}