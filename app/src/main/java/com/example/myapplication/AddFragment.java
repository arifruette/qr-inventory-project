package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddFragment extends Fragment {
    EditText name;
    EditText serial_number;
    EditText place;
    private static final String POST_URL = "http://192.168.43.245:5000/add_object";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View add_view = inflater.inflate(R.layout.fragment_add, container, false);
        // Inflate the layout for this fragment
        name = add_view.findViewById(R.id.nameObject);
        serial_number = add_view.findViewById(R.id.serial_numberObj);
        place = add_view.findViewById(R.id.placeObj);

//        if(name.getText().toString().equals("")){
//            name.setError("Обязательное поле!");
//            name.requestFocus();
//        }
        Button add = add_view.findViewById(R.id.add_objBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag_name = true;
                boolean flag_serial_number = true;
                boolean flag_place = true;
                if(name.getText().toString().equals("")){
                    name.setError("Обязательное поле!");
                    flag_name = false;
                    name.requestFocus();
                }
                if(serial_number.getText().toString().equals("")){
                    serial_number.setError("Обязательное поле!");
                    flag_serial_number = false;
                    serial_number.requestFocus();

                }
                if(place.getText().toString().equals("")){
                    place.setError("Обязательное поле!");
                    flag_place = false;
                    place.requestFocus();
                }
                if(flag_name & flag_place & flag_serial_number){
                    //TODO
                    send_post p = new send_post();
                    p.execute();
                }
            }
        });
        return add_view;
    }


    private class send_post extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... Void) {
            //JSONObject json = new JSONObject();
            Map<String, Map<String, String>> payload = new HashMap<>();
            Map<String, String> json = new HashMap<>();
            try {
                json.put("name", name.getText().toString());
                json.put("serial_number", serial_number.getText().toString());
                json.put("obj_place", place.getText().toString());
                payload.put("Form Data", json);
                Log.d("OMGGG", payload.toString());
                Log.d("BUBUBU", json.toString());
                Document doc = Jsoup
                        .connect(POST_URL)
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Accept", "text/html")
                        .data("name", name.getText().toString())
                        .data("serial_number", serial_number.getText().toString())
                        .method(Connection.Method.POST)
                        .ignoreContentType(true)
                        .userAgent(USER_AGENT)
                        .post();
                Log.d("TAGGG", doc.text());
                //TODO TODO TODO TODO TODO
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String unused) {
        }
    }

}