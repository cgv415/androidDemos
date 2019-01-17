package com.example.carlos.parcelable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    /*Pasar lista de objetos por intent*/

    private EditText nombre,dificultad,nombre2,dificultad2;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre = (EditText) findViewById(R.id.tvNombre2);
        dificultad= (EditText) findViewById(R.id.etDificultad2);
        nombre2= (EditText) findViewById(R.id.etNombre3);
        dificultad2= (EditText) findViewById(R.id.etDificultad3);
        btnGuardar= (Button) findViewById(R.id.btnGuardar2);
        btnGuardar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ArrayList<Trick> array = new ArrayList<>();
        array.add(new Trick(nombre.getText().toString(),dificultad.getText().toString()));
        array.add(new Trick(nombre2.getText().toString(),dificultad2.getText().toString()));

        Intent intent = new Intent(getApplicationContext(),ViewTrick2.class);
        intent.putExtra("Trucos",array);
        startActivity(intent);
    }
}
