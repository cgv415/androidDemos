package com.example.carlos.parcelable;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ViewTrick extends AppCompatActivity {

    private TextView nombre;
    private TextView dificultad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trick);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre = (TextView) findViewById(R.id.tvNombre);
        dificultad = (TextView) findViewById(R.id.tvDificultad);

        Bundle extras = getIntent().getExtras();

        Trick trick = extras.getParcelable("Truco");

        nombre.setText(trick.getNombre());
        dificultad.setText(trick.getDificultad());
    }

}
