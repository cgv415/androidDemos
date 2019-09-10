package com.example.carlos.basededatos;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DataBaseManager manager;
    private AutoCompleteTextView auto;

    private Button btnRead;
    private Button btnCreate;
    private Button btnReset;

    private EditText etNombre;
    private EditText etCurso;

    private TextView id;

    private int codigo;

    private ArrayList<ArrayList<String>> alumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        manager = new DataBaseManager(getApplicationContext());

        manager.reiniciarTabla();

        this.actualizarComplete();
        id =(TextView) findViewById(R.id.tvID);
        if(alumnos.isEmpty()){
            codigo = 0;
        }else{
            codigo = alumnos.size()+1;
        }
        id.setText(""+codigo);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);

        btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);
    }

    public void actualizarComplete(){
        alumnos = manager.obtenerAlumnos();
        String[] nombres = new String[alumnos.size()];
        for(int i = 0; i < alumnos.size();i++){
            nombres[i] = alumnos.get(i).get(1);
        }
        //Recoges el autocompletar de la interfaz
        auto = (AutoCompleteTextView) findViewById(R.id.autoNombre);
        //Creas un adaptador
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,nombres);
        //El número de letras necesario para activar el autocompletar
        auto.setThreshold(3);
        //implementas el adaptador en el autocompletar
        auto.setAdapter(adapter);

        auto.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), LeerUsuario.class);
                intent.putExtra("mensaje",auto.getText().toString());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCreate:
                etNombre = (EditText)findViewById(R.id.etNombre);
                etCurso = (EditText)findViewById(R.id.etCurso);
                if(manager.insertarAlumno(codigo,etNombre.getText().toString(),etCurso.getText().toString())){
                    codigo++;
                    id.setText("" + codigo);
                    etNombre.setText("");
                    etCurso.setText("");
                    this.actualizarComplete();
                    Toast.makeText(getApplicationContext(),"Alumno insertado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Fallo al insertar alumno",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnReset:
                manager.crearTablaAlumno();
                codigo = 0;
                id.setText("" + codigo);
                break;
        }
    }
}
