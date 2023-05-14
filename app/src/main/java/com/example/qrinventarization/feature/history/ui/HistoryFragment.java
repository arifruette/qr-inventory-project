package com.example.qrinventarization.feature.history.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.qrinventarization.databinding.FragmentHistoryBinding;
import com.example.qrinventarization.domain.model.history.History;
import com.example.qrinventarization.feature.history.presentation.HistoryStatus;
import com.example.qrinventarization.feature.history.presentation.HistoryViewModel;
import com.example.qrinventarization.feature.history.ui.recycler.HistoryAdapter;

import java.util.List;

public class HistoryFragment extends Fragment {
    private FragmentHistoryBinding binding;
    private HistoryViewModel viewModel;
    private HistoryAdapter adapter;
    private HistoryFragmentArgs args;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater);
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        args = HistoryFragmentArgs.fromBundle(requireArguments());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new HistoryAdapter();

        binding.historyRecycler.setAdapter(adapter);

        viewModel.status.observe(getViewLifecycleOwner(), this::renderStatus);
        viewModel.histories.observe(getViewLifecycleOwner(), this::renderData);

        viewModel.history(args.getId());

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigateUp();
            }
        });
    }


    public void renderStatus(HistoryStatus status){
        switch (status){

            case LOADED:

                binding.historyLoading.setVisibility(View.INVISIBLE);
                binding.errorHistory.setVisibility(View.INVISIBLE);
                binding.noHistory.setVisibility(View.INVISIBLE);

                binding.historyRecycler.setVisibility(View.VISIBLE);
                binding.historyTitle.setVisibility(View.VISIBLE);
                binding.buttonBack.setVisibility(View.VISIBLE);

                break;
            case FAILURE:

                binding.historyLoading.setVisibility(View.INVISIBLE);
                binding.errorHistory.setVisibility(View.VISIBLE);
                binding.noHistory.setVisibility(View.INVISIBLE);

                binding.historyRecycler.setVisibility(View.INVISIBLE);
                binding.historyTitle.setVisibility(View.VISIBLE);
                binding.buttonBack.setVisibility(View.VISIBLE);

                break;
            case LOADING:

                binding.historyLoading.setVisibility(View.VISIBLE);
                binding.errorHistory.setVisibility(View.INVISIBLE);
                binding.noHistory.setVisibility(View.INVISIBLE);

                binding.historyRecycler.setVisibility(View.INVISIBLE);
                binding.historyTitle.setVisibility(View.VISIBLE);
                binding.buttonBack.setVisibility(View.VISIBLE);

                break;
        }
    }

    public void renderData(List<History> historyList){
        binding.noHistory.setVisibility(historyList.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        adapter.setData(historyList);
    }
}
