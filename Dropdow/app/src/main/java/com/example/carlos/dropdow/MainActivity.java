package com.example.carlos.dropdow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ArrayList<String> langs;
    Map<String,ArrayList<String>> topics;
    android.widget.ExpandableListAdapter listAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        expandableListView = (ExpandableListView) findViewById(R.id.lv_dropdow);
        fillData();

        listAdapter = new ExpandableListAdapter(this,langs,topics);

        expandableListView.setAdapter(listAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(getApplicationContext(),langs.get(groupPosition)+ ":" + topics.get(langs.get(groupPosition)).get(childPosition),Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    public void fillData(){
        langs = new ArrayList<>();
        topics = new HashMap<>();

        langs.add("Java");
        langs.add("C");

        ArrayList<String> java = new ArrayList<>();
        ArrayList<String> c = new ArrayList<>();

        java.add("Super");
        java.add("Encapsulation");
        java.add("Methods");

        c.add("Procedure");
        c.add("Pointers");
        c.add("Array");

        topics.put(langs.get(0),java);
        topics.put(langs.get(1),c);
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
