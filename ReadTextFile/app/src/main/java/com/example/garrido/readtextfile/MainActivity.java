package com.example.garrido.readtextfile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<String> rutasArchivos;
    private String directorioRaiz;
    private TextView carpetaActual;
    private ListView listas;

    private TextView texto;
    private EditText etTexto;

    private final String rutaActual = "Ruta actual: ";

    private static String tipo;
    public static String ruta;

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
                popUp();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        texto = findViewById(R.id.tv_texto);
    }

    public void popUpEscribirTexto(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View v = inflater.inflate(R.layout.escribir_texto, null);

        etTexto = v.findViewById(R.id.et_texto);


        builder.setTitle("Escribir Texto");
        builder.setView(v);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(v.getContext(),Explorador.class);
                intent.putExtra("tipo","leer");
                startActivity(intent);
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
    }

    public void popUp() {
        // Use the Builder class for convenient dialog construction

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String [] opciones = {"Escribir texto","Leer texto"};

        builder.setTitle("Texto");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    tipo = "escribir";
                    //popUp();
                    popUpEscribirTexto();
                }else{
                    tipo = "leer";
                    Intent intent = new Intent(builder.getContext(),Explorador.class);
                    intent.putExtra("tipo","leer");
                    startActivity(intent);
                    //leerTexto("algo");
                }

                //popUpDirectorio();
            }
        });
        builder.show();
    }

    public void escribirTexto(String ruta){
        try {
            File fav;
            //fav = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    //"/Android/data/com.example.garrido.readtextfile/","fichero.txt");

            fav = new File(     ruta,"fichero.txt");

            //fav.createNewFile();
            OutputStreamWriter fout1 = new OutputStreamWriter(new FileOutputStream(fav));

            fout1.write(etTexto.getText().toString());

            fout1.close();

            Toast.makeText(this,fav.getAbsolutePath(),Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void leerTexto(String direccion){
        try{

            File file;
            file = new File(ruta,"fichero.txt");

            BufferedReader fin = new BufferedReader(new FileReader(file));

            String txt = fin.readLine();
            //Toast.makeText(this,txt,Toast.LENGTH_LONG).show();
            fin.close();

            texto.setText(txt);
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(ruta != null){
            if(tipo.equals("leer")){
                leerTexto(ruta);
            }else{
                escribirTexto(ruta);
            }
        }

        /*if(ruta == null){
            ruta = "Ningun archivo seleccionado";
        }*/

        //Toast.makeText(this,ruta,Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void popUpDirectorio(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View v = inflater.inflate(R.layout.content_explorador, null);

        carpetaActual = v.findViewById(R.id.rutaActual);
        listas = v.findViewById(R.id.listview_Lista);

        directorioRaiz = Environment.getExternalStorageDirectory().getPath();
        carpetaActual.setText(String.format("%s%s", rutaActual, directorioRaiz));

        listas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                File archivo = new File(rutasArchivos.get(i));

                if(archivo.isFile()){
                    Toast.makeText(view.getContext(),"Has seleccionado el archivo: " + archivo.getName(),Toast.LENGTH_LONG).show();
                }else{

                    carpetaActual.setText(String.format("%s%s", rutaActual, rutasArchivos.get(i)));
                    verDirectorio(rutasArchivos.get(i));
                }
            }
        });
        verDirectorio(directorioRaiz);

        builder.setTitle("Directorio");
        builder.setView(v);
        builder.setPositiveButton("Seleccionar carpeta", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
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
