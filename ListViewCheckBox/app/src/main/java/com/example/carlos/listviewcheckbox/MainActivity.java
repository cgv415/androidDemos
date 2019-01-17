package com.example.carlos.listviewcheckbox;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    /*https://www.youtube.com/watch?v=kFJgy_PPtqU*/

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);
        final ArrayList<String> ingredientes = new ArrayList<>();

        ingredientes.add("Pimiento");
        ingredientes.add("Cebolla");
        ingredientes.add("Tomate");
        ingredientes.add("Lechuga");
        ingredientes.add("Mayonesa");
        ingredientes.add("Ali-Oli");
        ingredientes.add("Ketchup");

        String ing = "Pimiento";

        AdapterDatos adapter = new AdapterDatos(this,ingredientes,ing);
        listView.setAdapter(adapter);

        listView.setItemsCanFocus(true);
        Log.d("Has seleccionado","setItemsCanFocus(true)");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("Se ha entrado en ","onItemClick");
                //Toast.makeText(getApplicationContext(),"onItemClick",Toast.LENGTH_SHORT).show();
                String st = ingredientes.get(position);

                ListView lv = (ListView) parent;
                if(lv.isItemChecked(position)){
                    Toast.makeText(getBaseContext(), "You checked " + ingredientes.get(position), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), "You unchecked " + ingredientes.get(position), Toast.LENGTH_SHORT).show();

                }

                /*
                ListView lv = (ListView) arg0;
                if(lv.isItemChecked(position)){
                    Toast.makeText(getBaseContext(), "You checked " + countries[position], Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), "You unchecked " + countries[position], Toast.LENGTH_SHORT).show();
                }*/

                //Log.d("check box ",listView.getSelectedItem().toString());

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Se ha entrado en ","onItemLongClick");
                Toast.makeText(getApplicationContext(),"onItemLongClick",Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Se ha entrado en ","OnItemSelectedListener");
                Toast.makeText(getApplicationContext(),"onItemSelected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
