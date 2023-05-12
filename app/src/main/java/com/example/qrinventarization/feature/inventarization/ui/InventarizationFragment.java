package com.example.qrinventarization.feature.inventarization.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.qrinventarization.ScannerScreen;
import com.example.qrinventarization.databinding.FragmentInventarizationBinding;
import com.example.qrinventarization.domain.model.items.Item;
import com.example.qrinventarization.feature.inventarization.presentation.InventarizationStatus;
import com.example.qrinventarization.feature.inventarization.presentation.InventarizationViewModel;
import com.example.qrinventarization.feature.inventarization.ui.recycler.InventarizationAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class InventarizationFragment extends Fragment {

    private FragmentInventarizationBinding binding;
    private InventarizationFragmentArgs args;
    private HashMap<String, ArrayList<Item>> inventarizationItems;
    private InventarizationViewModel viewModel;
    private ArrayList<String> locations;
    private ArrayAdapter<String> adapter;

    private HashMap<Long, Boolean> checked_serial_numbers = new HashMap<>();
    private InventarizationAdapter adapter_objects;
    private long onBackPressedTime;
    private Toast backToast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if(onBackPressedTime + 2000 > System.currentTimeMillis()){
                    backToast.cancel();
                    Navigation.findNavController(binding.getRoot()).navigateUp();
                }else{
                    backToast = Toast.makeText(getContext(), "Вы уверены что хотите покинуть окно инвентаризации?" + "\n" + "Прогресс не сохранится", Toast.LENGTH_SHORT);
                    backToast.setGravity(Gravity.BOTTOM, 0, 250);
                    backToast.show();
                }
                onBackPressedTime = System.currentTimeMillis();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInventarizationBinding.inflate(inflater);
        viewModel = new ViewModelProvider(this).get(InventarizationViewModel.class);
        args = InventarizationFragmentArgs.fromBundle(requireArguments());
        adapter_objects = new InventarizationAdapter();

        if(savedInstanceState == null) {
            viewModel.load();

        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.status.observe(getViewLifecycleOwner(), InventarizationFragment.this::renderStatus);
        viewModel.items.observe(getViewLifecycleOwner(), this::renderItems);
        binding.locationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Выберите помещение:    ")){

                }else{
                    //System.out.println(((List<Item>) inventarizationItems.get(parent.getItemAtPosition(position).toString())));
                    adapter_objects.setItems((List<Item>) inventarizationItems.get(parent.getItemAtPosition(position).toString()));
                    binding.rV.setAdapter(adapter_objects);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(InventarizationFragment.this);
                intentIntegrator.setPrompt("Сканируйте QR-код");
                intentIntegrator.setOrientationLocked(true);

                intentIntegrator.setCameraId(0);
                intentIntegrator.setCaptureActivity(ScannerScreen.class);
                //intentIntegrator.setTorchEnabled(true);//включает фонарик
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.initiateScan();
            }
        });
        binding.stopProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(String key:inventarizationItems.keySet()){
                    for(Item item: inventarizationItems.get(key)){
                        if(!item.isChecked()){
                            checked_serial_numbers.put(item.getId(), false);
                        }
                    }
                }
                Navigation.findNavController(getView()).navigateUp();
                Toast toast = Toast.makeText(getContext(), "Данные были сохранены", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }

        });
    }

    private void renderItems(List<Item> itemsList){
        inventarizationItems = new HashMap<>();

        for(int i =0;i < itemsList.size();i++){

            if(!itemsList.get(i).getPlace().equals("None")){
                String place = itemsList.get(i).getPlace().replace(".0", "");
                if(Arrays.asList(args.getLocations().split(" ")).contains(place)){
                    if(inventarizationItems.containsKey(place)){
                        ArrayList<Item> final_list = inventarizationItems.get(place);
                        final_list.add(itemsList.get(i));
                        inventarizationItems.put(place, final_list);
                    }else{
                        ArrayList<Item> final_list = new ArrayList<>();
                        final_list.add(itemsList.get(i));
                        inventarizationItems.put(place, final_list);
                    }
                }
            }
        }
        System.out.println(inventarizationItems);
        locations = new ArrayList<>();
        locations.addAll(inventarizationItems.keySet());

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, locations);
        binding.locationsSpinner.setPrompt("Выберите помещение");
        binding.locationsSpinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //System.out.println(inventarizationItems.toString());
    }

    private void renderStatus(InventarizationStatus status){
        switch (status){

            case LOADING:
                binding.locationsSpinner.setVisibility(View.INVISIBLE);
                binding.rV.setVisibility(View.INVISIBLE);
                binding.scan.setVisibility(View.INVISIBLE);
                binding.locationsSpinner.setVisibility(View.INVISIBLE);
                binding.errorInvent.setVisibility(View.INVISIBLE);

                binding.contentLoading.setVisibility(View.VISIBLE);
                break;
            case LOADED:
                binding.locationsSpinner.setVisibility(View.VISIBLE);
                binding.rV.setVisibility(View.VISIBLE);
                binding.scan.setVisibility(View.VISIBLE);
                binding.locationsSpinner.setVisibility(View.VISIBLE);

                binding.errorInvent.setVisibility(View.INVISIBLE);

                binding.contentLoading.setVisibility(View.INVISIBLE);

                break;

            case FAILURE:
                binding.locationsSpinner.setVisibility(View.INVISIBLE);
                binding.rV.setVisibility(View.INVISIBLE);
                binding.scan.setVisibility(View.INVISIBLE);
                binding.locationsSpinner.setVisibility(View.INVISIBLE);

                binding.errorInvent.setVisibility(View.VISIBLE);

                binding.contentLoading.setVisibility(View.INVISIBLE);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null) {
            Log.d("TESTQR", " " + intentResult.getContents() + " " + intentResult.getFormatName());
            if (intentResult.getContents() == null) {
                Toast.makeText(getContext(), "Отмена", Toast.LENGTH_SHORT).show();
            } else {
                List<Item> items = inventarizationItems.get(binding.locationsSpinner.getSelectedItem().toString());
                boolean flag = true;
                Log.d("TAG", intentResult.getContents());
                for(int i = 0; i < items.size();i++){
                    if(Objects.equals(items.get(i).getSerial_number(), intentResult.getContents())){
                        flag = false;
                        items.get(i).setCheckedItem(true);
                        checked_serial_numbers.put((Long) items.get(i).getId(), true);
                        adapter_objects.setItems(items);
                    }
                }
                if(flag){
                    Toast toast = Toast.makeText(getContext(), "Этого объекта нет в данном помещении", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 250);
                    toast.show();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}