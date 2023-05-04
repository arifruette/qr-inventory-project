package com.example.qrinventarization.feature.items.ui.recycler;

import static java.lang.Math.max;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrinventarization.databinding.ItemRecyclerBinding;
import com.example.qrinventarization.databinding.TestItemRecyclerBinding;
import com.example.qrinventarization.domain.model.items.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> implements Filterable {

    private ItemClickListener listener;

    private List<Item> items = new ArrayList<>();
    private List<Item> getItemModelListFilter = new ArrayList<>();

    public ItemAdapter(ItemClickListener listener){
        this.listener = listener;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.values = getItemModelListFilter;
                    filterResults.count = getItemModelListFilter.size();
                }else{
                    String searchStr = constraint.toString().toLowerCase();
                    List<Item> itemModels = new ArrayList<>();
                    for(Item item: getItemModelListFilter){
                        if(item.getName().toLowerCase().contains(searchStr) ||
                                item.getSerial_number().toLowerCase().contains(searchStr)){
                            itemModels.add(item);
                        }
                    }
                    filterResults.values = itemModels;
                    filterResults.count = itemModels.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (List<Item>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TestItemRecyclerBinding binding = TestItemRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Item> items) {
        int count = getItemCount();
        this.items = new ArrayList<>(items);

        this.getItemModelListFilter = items;

        notifyItemRangeChanged(0, max(count, getItemCount()));
    }


}
