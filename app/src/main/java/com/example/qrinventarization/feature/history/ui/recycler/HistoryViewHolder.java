package com.example.qrinventarization.feature.history.ui.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrinventarization.databinding.HistoryRecyclerBinding;
import com.example.qrinventarization.domain.model.history.History;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    private HistoryRecyclerBinding binding;


    public HistoryViewHolder(HistoryRecyclerBinding historyRecyclerBinding) {
        super(historyRecyclerBinding.getRoot());
        this.binding = historyRecyclerBinding;
    }


    public void bind(History history){
        binding.date.setText(history.getDate());
        //TODO надо поменять апишку у истории
        binding.newPlace.setText(history.getNew_place());
        binding.oldPlace.setText(history.getOld_place());
    }
}
