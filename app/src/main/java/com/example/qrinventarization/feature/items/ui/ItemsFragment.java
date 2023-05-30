package com.example.qrinventarization.feature.items.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.qrinventarization.R;
import com.example.qrinventarization.databinding.FragmentItemsBinding;
import com.example.qrinventarization.domain.model.items.Item;
import com.example.qrinventarization.feature.items.presentation.ItemsStatus;
import com.example.qrinventarization.feature.items.presentation.ItemsViewModel;
import com.example.qrinventarization.feature.items.ui.recycler.ItemAdapter;

import java.util.List;

public class ItemsFragment extends Fragment {

    private ItemsViewModel viewModel;
    private ItemAdapter adapter;

    private FragmentItemsBinding binding;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ItemsViewModel.class);

        binding = FragmentItemsBinding.inflate(inflater);
        sharedPreferences = getContext().getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ItemAdapter(id -> Navigation.findNavController(binding.getRoot()).navigate(ItemsFragmentDirections.actionItemsFragmentToObject(id)));


        binding.recycler.setAdapter(adapter);
        viewModel.status.observe(getViewLifecycleOwner(), this::renderStatus);
        viewModel.items.observe(getViewLifecycleOwner(), this::renderItems);
        viewModel.load(sharedPreferences.getString("token", "none"));
    }

    private void renderStatus(ItemsStatus status){
        switch (status){

            case LOADING:
                binding.empty.setVisibility(View.INVISIBLE);
                binding.recycler.setVisibility(View.INVISIBLE);
                binding.error.setVisibility(View.INVISIBLE);
                binding.progress.setVisibility(View.VISIBLE);

                break;
            case LOADED:


                binding.recycler.setVisibility(View.VISIBLE);
                binding.error.setVisibility(View.INVISIBLE);
                binding.progress.setVisibility(View.INVISIBLE);
                break;

            case FAILURE:
                binding.empty.setVisibility(View.INVISIBLE);
                binding.recycler.setVisibility(View.INVISIBLE);
                binding.error.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void renderItems(List<Item> lst){
        binding.empty.setVisibility(lst.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        adapter.setItems(lst);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Введите название предмета");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}