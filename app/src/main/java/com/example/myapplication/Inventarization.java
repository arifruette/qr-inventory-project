package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Inventarization extends Fragment {
    private Map<String, String[]> itemsForInventarization;
    private ArrayList<Object> rooms;
    private String serial_number;
    private String keyValue;
    private ObjectsAdapter oa;
    private Integer pos;
    private List<Item> l;
    private FragmentTransaction fTrans;
    private long onBackPressedTime;
    private Toast backToast;

    public static Map<String, String[]> bundleToMap(Bundle extras) {
        Map<String, String[]> map = new HashMap<>();

        Set<String> ks = extras.keySet();
        for (String key : ks) {
            map.put(key, extras.getString(key).split(";"));

        }
        return map;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if(onBackPressedTime + 2000 > System.currentTimeMillis()){
                    backToast.cancel();

                    LocationsFragment fragment = new LocationsFragment();
                    fTrans = getFragmentManager().beginTransaction();

                    Inventarization fragment1 = new Inventarization();

                    fTrans.addToBackStack(null);
                    fTrans.replace(R.id.navHostFragment, fragment);

                    fTrans.setReorderingAllowed(true);



                    fTrans.commit();
                }else{
                    backToast = Toast.makeText(getContext(), "Вы уверены что хотите покинуть окно инвентаризации?" + "\n" + "Прогресс не сохранится", Toast.LENGTH_SHORT);
                    backToast.show();
                }
                onBackPressedTime = System.currentTimeMillis();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }


    public boolean check(String num, String key){
        int count = 0;
        for(String elem: itemsForInventarization.get(key)){
                String[] omg = elem.split(" ");
                if(Objects.equals(omg[omg.length - 1], num)){
                    pos = count;
                    return true;
                }
                count++;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View content = inflater.inflate(R.layout.fragment_inventarization, container, false);
        Bundle bndl = getArguments();
        itemsForInventarization = bundleToMap(bndl.getBundle("omg"));


        Spinner spinner = content.findViewById(R.id.locationsSpinner);
        RecyclerView objects = content.findViewById(R.id.rV);
        Button btnScan = content.findViewById(R.id.startScan);

        rooms = new ArrayList<>();
        for (Object key: itemsForInventarization.keySet()){
            rooms.add(key);
        }
        rooms.add(0, "Выберите помещение:");

        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, rooms);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Выберите помещение:")){
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    keyValue = item;
                    //Toast.makeText(parent.getContext(),"Selected: " + position, Toast.LENGTH_SHORT).show();

                    String[] s = itemsForInventarization.get(item);
                    l = new ArrayList<>();
                    for(String i: s){
                        Item item1 = new Item(i, false);
                        l.add(item1);
                    }
                    oa = new ObjectsAdapter();
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    objects.setLayoutManager(llm);
                    objects.setAdapter(oa);
                    oa.setData(l);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(Inventarization.this);
                intentIntegrator.setPrompt("Сканируйте QR-код");
                intentIntegrator.setOrientationLocked(false);

                //intentIntegrator.setTorchEnabled(true);//включает фонарик
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.initiateScan();
            }
        });
        return content;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            Log.d("TESTQR", " " + intentResult.getContents() + " " + intentResult.getFormatName());
            if (intentResult.getContents() == null) {
                Toast.makeText(getContext(), "Отмена", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                serial_number = intentResult.getContents();
                if(check(serial_number, keyValue)){
                    //TODO
                    l.get(pos).checked = true;
                    oa.setData(l);
                }else{
                    Toast.makeText(getContext(), "В данном помещении нет этого объекта", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}