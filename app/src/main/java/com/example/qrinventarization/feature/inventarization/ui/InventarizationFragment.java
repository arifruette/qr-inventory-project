package com.example.qrinventarization.feature.inventarization.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qrinventarization.R;
import com.example.qrinventarization.databinding.FragmentInventarizationBinding;

import java.util.Arrays;

public class InventarizationFragment extends Fragment {

    private FragmentInventarizationBinding binding;
    private SharedPreferences sp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInventarizationBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sp = getActivity().getSharedPreferences("mysett", Context.MODE_PRIVATE);
        String[] lol = sp.getString("places", "null").split(" ");
        System.out.println(Arrays.toString(lol));
    }
}