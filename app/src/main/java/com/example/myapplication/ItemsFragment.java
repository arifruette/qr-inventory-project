package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsFragment extends Fragment implements View.OnCreateContextMenuListener {
    private ListView listView;
    private ContentLoadingProgressBar contentLoadingProgressBar;
    private Map<String, Float> indexes = new HashMap<String, Float>();

    ArrayAdapter<String> adapter;
    List<String> lst = new ArrayList<String>();

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
    private static final String GET_URL = "http://192.168.43.245:5000/api/objects";
    private static final String DELETE_URL = "http://192.168.43.245:5000/api/object_delete/";


    JSONArray params;
    Integer OBJ_NUMBER;

    boolean flag = true;
    List<Float> wrong;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_items, container, false);


        parser dw = new parser();
        if (flag) {
            doSMTH(dw);
        }
        flag = false;


        listView = contentView.findViewById(R.id.listview);
        contentLoadingProgressBar = contentView.findViewById(R.id.loadingProgressBarItems);


        // sample data
        adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                lst
        ){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }

            @Override
            public int getPosition(String item) {
                return super.getPosition(item);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }


        };


        listView.setAdapter(adapter);


        registerForContextMenu(listView);

        listView.setVisibility(View.GONE);
        contentLoadingProgressBar.setVisibility(View.VISIBLE);

        return contentView;
    }

    public void doSMTH(parser obj) {
        obj.execute();
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);


//        menu.setHeaderTitle("Options");
//        menu.add(Menu.NONE, 0, menu.NONE, "Edit");
//        menu.add(Menu.NONE, 1, menu.NONE, "Delete");

    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                // TODO: Add edit code
                break;
            case R.id.delete:
                // TODO: FIX DELETING
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Log.d("OMGG", " " + info.position);
                String st = lst.get(info.position);
                String parts[] = st.split(" ");
                String name = parts[parts.length - 1];
                Integer ind = Math.round(indexes.get(name));
                try {
                    deleteObject(ind, info.position);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                adapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }


    private class parser extends AsyncTask<Void, Void, String> {


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
                params = obj.getJSONArray("objects");

                for (int i = 0; i < params.length(); i++) {

                    //Adding each element of JSON array into ArrayList
                    Object a = params.get(i);
                    HashMap map = new Gson().fromJson(a.toString(), HashMap.class);
                    String blob = map.get("name") + " " + map.get("serial_number").toString();
                    lst.add(blob);
                    if(!indexes.containsKey(map.get("serial_number"))){
                        indexes.put(map.get("serial_number") + "", Float.valueOf(map.get("id").toString()));
                    }
                    else{
                        wrong.add(Float.valueOf(map.get("id").toString()));
                    }



                }
                listView.setVisibility(View.VISIBLE);
                contentLoadingProgressBar.setVisibility(View.GONE);

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + unused + "\"");
            }

        }
    }
    public void deleteObject(Integer num, Integer i) throws IOException {
        OBJ_NUMBER = num;
        lst.remove(lst.get(i));
        Log.d("INDEx", i.toString());
        deleter d = new deleter();
        d.execute();
//
    }

    private class deleter extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... Void) {
            String docs = null;
            try {
                docs = Jsoup.connect(DELETE_URL + OBJ_NUMBER)
                        .userAgent(USER_AGENT)
                        .method(Connection.Method.GET)
                        .ignoreContentType(true)
                        .execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("TAGGG", docs + " " + OBJ_NUMBER);
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

