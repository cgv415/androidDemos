package com.example.carlos.listviewcheckbox;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

/**
 * Clase generada por Carlos Garrido para la aplicación ComandUal en 14/11/2016
 */

public class AdapterDatos extends BaseAdapter {
    private Activity activity;
    private ArrayList<String> ingredientes;

    private String ing;


    public AdapterDatos(Activity activity, ArrayList<String> datos,String ing) {
        this.activity = activity;
        this.ingredientes = datos;
        this.ing = ing;
    }

    @Override
    public int getCount() {
        return ingredientes.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        //return ingredientes.get(position).;
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.datoslista,null);
        }
        CheckBox dato = (CheckBox) v.findViewById(R.id.checkBox);
        dato.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int x = 0;
                String st = buttonView.getText().toString();
            }
        });
        dato.setText(ingredientes.get(position));
        if(ingredientes.get(position).equals(ing)){
            dato.setChecked(true);
        }

        return v;
    }
}
