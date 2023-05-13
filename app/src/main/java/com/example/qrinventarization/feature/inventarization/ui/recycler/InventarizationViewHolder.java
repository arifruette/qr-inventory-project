package com.example.qrinventarization.feature.inventarization.ui.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrinventarization.databinding.FragmentInventarizationBinding;
import com.example.qrinventarization.databinding.InventRecycleBinding;
import com.example.qrinventarization.domain.model.items.Item;

public class InventarizationViewHolder extends RecyclerView.ViewHolder {

    private InventRecycleBinding binding;


    public InventarizationViewHolder(InventRecycleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Item item){
        binding.checkbox1.setChecked(item.getChecked() == 2);
        binding.tv.setText(item.getName() + "\n" +item.getSerial_number());
    }
}
