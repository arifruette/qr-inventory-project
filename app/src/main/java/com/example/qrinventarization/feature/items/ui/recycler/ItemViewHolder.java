package com.example.qrinventarization.feature.items.ui.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.qrinventarization.databinding.ItemRecyclerBinding;
import com.example.qrinventarization.domain.model.Item;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private ItemRecyclerBinding binding; //TODO НЕ уверен
    private ItemClickListener listener;

    public ItemViewHolder(ItemRecyclerBinding binding, ItemClickListener listener){
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }
    public void bind(Item item){
        binding.name.setText(item.getName());
        binding.number.setText(item.getSerial_number());
        binding.place.setText(item.getPlace());
        binding.getRoot().setOnClickListener(v -> listener.onClick(item.getId()));
    }
}