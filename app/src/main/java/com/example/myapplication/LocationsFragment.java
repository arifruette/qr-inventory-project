package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LocationsFragment extends Fragment {

    private ListView locationsList;
    private ContentLoadingProgressBar contentLoadingProgressBar;
    FragmentTransaction fTrans;

    List<String> classes = new ArrayList<String>();
    ArrayList<String> checked;

    Map<String, ArrayList<String>> itemsForInventarization;
    Map<Integer, String> place_id;
    Button startInventarization;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
    private static final String GET_URL = "http://192.168.43.245:5000/api/places";
    private static final String GET_URL_OBJECTS = "http://192.168.43.245:5000/api/objects";
    JSONArray params;
    boolean flag = true;



    public Bundle convertMapToBundle(Map<String, ArrayList<String>> data){
        Bundle bundle = new Bundle();
        for (Map.Entry<String, ArrayList<String>> entry : data.entrySet()) {
            //TODO
            List<String> l = entry.getValue();
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i < l.size();i++){
                sb.append(l.get(i));
                sb.append(";");
            }
            bundle.putString(entry.getKey(), sb.toString());
        }
        return bundle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_locations, container, false);

        startInventarization = view.findViewById(R.id.startProcess);


        parser dw = new parser();
        if(flag){
            doSMTH(dw);
        }
        flag = false;


        locationsList = view.findViewById(R.id.locationsList);
        contentLoadingProgressBar = view.findViewById(R.id.loadingProgressBar);



        ArrayAdapter<String> locations_adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_multiple_choice, classes);

        locationsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        locationsList.setAdapter(locations_adapter);

        locationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }

        });

        locationsList.setVisibility(View.GONE);
        contentLoadingProgressBar.setVisibility(View.VISIBLE);


        startInventarization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cntChoice = locationsList.getCount();

                checked = new ArrayList<>();

                SparseBooleanArray sparseBooleanArray = locationsList
                        .getCheckedItemPositions();
                for (int i = 0; i < cntChoice; i++) {

                    if (sparseBooleanArray.get(i)) {
                        String[] post = locationsList.getItemAtPosition(i).toString().split(" ");
                        checked.add(post[0]);
                    }
                }

                get_object go = new get_object();
                go.execute();




            }
        });

        return view;
    }

    public void doSMTH(parser obj){
        obj.execute();
    }


    private class parser extends AsyncTask<Void, Void, String>   {

        @Override
        protected String doInBackground(Void... Void) {
            Document document = null;
            try {
                document = Jsoup.connect(GET_URL)
                        .method(Connection.Method.GET)
                        .timeout(10_000)
                        .userAgent(USER_AGENT)
                        .ignoreContentType(true)
                        .execute().parse();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements elements = document.getElementsByTag("body");
            return elements.text();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String unused) {
            try {

                JSONObject obj = new JSONObject(unused);

                Log.d("My App", obj.toString());
                params = obj.getJSONArray("places");
                place_id = new HashMap<>();

                for (int i=0;i<params.length();i++) {

                    //Adding each element of JSON array into ArrayList
                    Object a = params.get(i);
                    HashMap map = new Gson().fromJson(a.toString(), HashMap.class);
                    String blob = map.get("text").toString().replace(".0", "") + " " + "кабинет";
                    classes.add(blob);
                    Double id = (Double) map.get("id");
                    Integer d = id.intValue();
                    place_id.put(d, map.get("text").toString());
                }
                locationsList.setVisibility(View.VISIBLE);
                contentLoadingProgressBar.setVisibility(View.GONE);

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + unused + "\"");
            }

        }
    }

    private class get_object extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... Void) {
            String elements;
            try {
                 elements = Jsoup.connect(GET_URL_OBJECTS)
                        .method(Connection.Method.GET)
                        .timeout(10_000)
                        .userAgent(USER_AGENT)
                        .ignoreContentType(true)
                        .get().getElementsByTag("body").text();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return elements;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String unused) {
            try {

                JSONObject obj = new JSONObject(unused);

                Log.d("My App", obj.toString());
                params = obj.getJSONArray("objects");
                itemsForInventarization = new HashMap<>();

                for (int i = 0; i < params.length(); i++) {

                    //Adding each element of JSON array into ArrayList
                    Object a = params.get(i);
                    HashMap map = new Gson().fromJson(a.toString(), HashMap.class);
                    String blob = map.get("name").toString() + " " + map.get("serial_number").toString();

                    if(map.get("obj_place") != null){
                        Double www  = (Double) map.get("obj_place");
                        Integer idi = www.intValue();
                        String place = place_id.get(idi).replace(".0", "");
                        if(checked.contains(place)){
                            if(itemsForInventarization.containsKey(place)){
                                ArrayList<String> lst_values = itemsForInventarization.get(place);
                                lst_values.add(blob);
                                itemsForInventarization.put(place, lst_values);
                            }else{
                                ArrayList<String> t = new ArrayList<>();
                                t.add(blob);
                                itemsForInventarization.put(place, t);
                            }
                        }
                    }
                }
                Inventarization fragment = new Inventarization();
                Bundle bundle = new Bundle();

                bundle.putBundle("omg", convertMapToBundle(itemsForInventarization));


                fragment.setArguments(bundle);


                fTrans = getFragmentManager().beginTransaction();

                LocationsFragment fragment1 = new LocationsFragment();

                fTrans.setReorderingAllowed(true);

                fTrans.addToBackStack(fragment1.getClass().getName());
                fTrans.replace(R.id.navHostFragment, fragment);

                fTrans.commit();



            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + unused + "\"");
            }



        }
    }


}