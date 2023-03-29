package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ObjectsAdapter extends RecyclerView.Adapter<ObjectsAdapter.ObjectsViewHolder> {

    private List<Item> objects;

    public void setData(List<Item> obj){
        this.objects = obj;
        notifyDataSetChanged();
    }

//    public void setTick()

    @NonNull
    @Override
    public ObjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.test,
                parent,
                false
        );
        return new ObjectsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectsViewHolder holder, int position) {
        String st = objects.get(position).name;
        holder.textView.setText(st);
        holder.checkBox.setChecked(objects.get(position).checked);
        holder.checkBox.setClickable(false);
    }


    @Override
    public int getItemCount() {
        return objects.size();
    }
    public ObjectsAdapter(){
        objects = new ArrayList<Item>();
    }

    class ObjectsViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private CheckBox checkBox;
        public ObjectsViewHolder(@NonNull View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
            checkBox = itemView.findViewById(R.id.checkbox1);
        }

    }
}
