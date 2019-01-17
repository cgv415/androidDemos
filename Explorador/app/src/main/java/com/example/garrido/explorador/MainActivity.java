package com.example.garrido.explorador;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<String> nombresArchivos;
    private List<String> rutasArchivos;
    private ArrayAdapter<String> adapter;
    private String directorioRaiz;
    private TextView carpetaActual;
    private ListView listas;
    private File directorioActual;

    private final String rutaActual = "Ruta actual: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carpetaActual = findViewById(R.id.rutaActual);
        listas = findViewById(R.id.listview_Lista);

        directorioRaiz = Environment.getExternalStorageDirectory().getPath();
        carpetaActual.setText(rutaActual + directorioRaiz);

        listas.setOnItemClickListener(this);
        verDirectorio(directorioRaiz);
    }

    public void verDirectorio(String rutaDirectorio){
        nombresArchivos = new ArrayList<>();
        rutasArchivos = new ArrayList<>();

        int count = 0;

        directorioActual = new File(rutaDirectorio);
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

        adapter = new ArrayAdapter<>(this,R.layout.lista_archivos,nombresArchivos);
        listas.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        File archivo = new File(rutasArchivos.get(i));

        if(archivo.isFile()){
            Toast.makeText(this,"Has seleccionado el archivo: " + archivo.getName(),Toast.LENGTH_LONG).show();
        }else{

            carpetaActual.setText(rutaActual + rutasArchivos.get(i));
            verDirectorio(rutasArchivos.get(i));
        }
    }
}
