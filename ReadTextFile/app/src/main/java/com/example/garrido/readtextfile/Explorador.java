package com.example.garrido.readtextfile;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Explorador extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private List<String> rutasArchivos;
    private String directorioRaiz;
    private TextView carpetaActual;
    private ListView listas;

    private final String rutaActual = "Ruta actual: ";

    private String camino;

    private String tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorador);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        tipo = bundle.getString("tipo");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.ruta = camino;
                finish();

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        carpetaActual = findViewById(R.id.rutaActual);
        listas = findViewById(R.id.listview_Lista);

        directorioRaiz = Environment.getExternalStorageDirectory().getPath();
        carpetaActual.setText(String.format("%s%s", rutaActual, directorioRaiz));

        listas.setOnItemClickListener(this);
        verDirectorio(directorioRaiz);
    }

    public void verDirectorio(String rutaDirectorio){
        List<String> nombresArchivos = new ArrayList<>();
        rutasArchivos = new ArrayList<>();

        int count = 0;

        File directorioActual = new File(rutaDirectorio);
        File[] listaArchivos = directorioActual.listFiles();

        if(!rutaDirectorio.equals(directorioRaiz)){
            nombresArchivos.add("../");
            rutasArchivos.add(directorioActual.getParent());
            count = 1;
        }

        for(File archivo
                : listaArchivos){
            rutasArchivos.add(archivo.getPath());
        }

        Collections.sort(rutasArchivos,String.CASE_INSENSITIVE_ORDER);

        for(int i = count; i < rutasArchivos.size(); i++){
            File archivo = new File(rutasArchivos.get(i));
            if(archivo.isFile()){
                nombresArchivos.add(archivo.getName());
            }else{
                nombresArchivos.add("/" + archivo.getName());
            }
        }

        if(listaArchivos.length < 1){
            nombresArchivos.add("No hay ningun archivo");
            rutasArchivos.add(rutaDirectorio);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.lista_archivos, nombresArchivos);
        listas.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        File archivo = new File(rutasArchivos.get(i));

        if(archivo.isFile()){
            //Toast.makeText(this,"Ruta: " + camino,Toast.LENGTH_LONG).show();
            MainActivity.ruta = camino;
            finish();
        }else{

            carpetaActual.setText(String.format("%s%s", rutaActual, rutasArchivos.get(i)));
            camino = rutasArchivos.get(i);
            verDirectorio(rutasArchivos.get(i));
        }
    }

}
