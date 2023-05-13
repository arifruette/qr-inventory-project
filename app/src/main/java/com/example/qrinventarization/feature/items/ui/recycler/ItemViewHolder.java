package com.example.qrinventarization.feature.items.ui.recycler;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrinventarization.R;
import com.example.qrinventarization.databinding.TestItemRecyclerBinding;
import com.example.qrinventarization.domain.model.items.Item;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TestItemRecyclerBinding binding;
    private ItemClickListener listener;

    public ItemViewHolder(TestItemRecyclerBinding binding, ItemClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public void bind(Item item) {
        binding.testItemName.setText(item.getName());
        binding.testItemNumber.setText(item.getSerial_number());
        System.out.println(item.getChecked());
        switch (item.getChecked()) {
            case 1:
                binding.itemStatus.setText("×");
                binding.itemStatus.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.red));
                break;
            case 2:
                binding.itemStatus.setText("✓");
                binding.itemStatus.setTextSize(14);
                binding.itemStatus.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.green));
                break;
            case 3:
                binding.itemStatus.setText("?");
                binding.itemStatus.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.colorBlue_Figma));
                break;
        }
        binding.getRoot().setOnClickListener(v -> listener.onClick(item.getId()));
    }
}
