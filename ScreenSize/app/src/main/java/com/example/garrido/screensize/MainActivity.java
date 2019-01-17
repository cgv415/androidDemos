package com.example.garrido.screensize;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(view.getContext(),AdaptativeActivity.class);
                startActivity(intent);
            }
        });
        int orientation = getResources().getConfiguration().orientation;
        String toastMsg = "";
        switch (orientation){
            case Configuration.ORIENTATION_PORTRAIT:
                toastMsg = "Vertical";
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                toastMsg = "Horizontal";
                break;
        }

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsg += ", Pantalla grande";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg += ", Pantalla Normal";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg += ", Pantalla pequeña";
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                toastMsg += ", Pantalla extra larga";
                break;
            case Configuration.SCREENLAYOUT_SIZE_UNDEFINED:
                toastMsg += ", No se reconoce";
                break;
            default:
                toastMsg += ", No se detecta";
        }

        Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
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
