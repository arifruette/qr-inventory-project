package com.example.qrinventarization.feature.logout.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qrinventarization.databinding.FragmentLogoutBinding;
import com.example.qrinventarization.feature.login.ui.LoginPage;

public class
LogoutFragment extends Fragment {


    private FragmentLogoutBinding binding;
    private SharedPreferences sharedPreferences;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(inflater);
        sharedPreferences = getActivity().getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();

        Intent intent = new Intent(getActivity().getBaseContext(), LoginPage.class);
        Toast.makeText(getContext(), "Logout successful", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}