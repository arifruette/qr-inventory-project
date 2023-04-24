package com.example.qrinventarization.feature.inventarization.ui.recycler;

import static java.lang.Math.max;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrinventarization.databinding.FragmentInventarizationBinding;
import com.example.qrinventarization.databinding.InventRecycleBinding;
import com.example.qrinventarization.domain.model.items.Item;
import com.google.android.material.datepicker.RangeDateSelector;

import java.util.List;

public class InventarizationAdapter extends RecyclerView.Adapter<InventarizationViewHolder> {

    private List<Item> items;
    private InventRecycleBinding binding;

    @NonNull
    @Override
    public InventarizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = InventRecycleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InventarizationViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull InventarizationViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setItems(List<Item> items) {
        int count = getItemCount();
        this.items = items;
        notifyItemRangeChanged(0, max(count, getItemCount()));
    }
}
