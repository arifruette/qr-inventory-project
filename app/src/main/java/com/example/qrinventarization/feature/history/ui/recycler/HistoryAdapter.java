package com.example.qrinventarization.feature.history.ui.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrinventarization.databinding.HistoryRecyclerBinding;
import com.example.qrinventarization.domain.model.history.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private List<History> historyList = new ArrayList<>();
    private HistoryRecyclerBinding binding;

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = HistoryRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(historyList.get(position));
    }

    @Override
    public int getItemCount() {
        return historyList.isEmpty() ? 0 : historyList.size();
    }

    public void setData(List<History> lst){
        int cnt = this.getItemCount();
        this.historyList = lst;
        notifyItemRangeChanged(0, Math.max(lst.size(), cnt));
    }
}
