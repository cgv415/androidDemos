package com.example.carlos.parcelable;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewTrick2 extends AppCompatActivity {

    private TextView nombre;
    private TextView dificultad;
    private TextView nombre2;
    private TextView dificultad2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trick2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        nombre = (TextView) findViewById(R.id.tvNombre2);
        dificultad = (TextView) findViewById(R.id.tvDificultad2);

        nombre2 = (TextView) findViewById(R.id.tvNombre3);
        dificultad2 = (TextView) findViewById(R.id.tvDificultad3);

        Bundle extras = getIntent().getExtras();

        ArrayList<Trick> tricks = extras.getParcelableArrayList("Trucos");


            nombre.setText(tricks.get(0).getNombre().toString());
            dificultad.setText(tricks.get(0).getDificultad().toString());

            nombre2.setText(tricks.get(1).getNombre().toString());
            dificultad2.setText(tricks.get(1).getDificultad().toString());

    }

}
