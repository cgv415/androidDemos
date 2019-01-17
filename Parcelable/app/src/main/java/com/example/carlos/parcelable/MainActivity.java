package com.example.carlos.parcelable;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*Tutorial seguido: https://www.youtube.com/watch?v=VSyue9VdnCQ*/
    /*http://www.parcelabler.com/*/
    /*Pasar un solo objeto por intent. Funciona*/

    private EditText nombre;
    private EditText dificultad;
    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre = (EditText) findViewById(R.id.etNombre);
        dificultad = (EditText) findViewById(R.id.etDificultad);
        guardar = (Button) findViewById(R.id.btnGuardar);
        guardar.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardar:
                Trick trick= new Trick(nombre.getText().toString(),dificultad.getText().toString());

                Intent intent = new Intent(getApplicationContext(),ViewTrick.class);
                intent.putExtra("Truco",trick);
                startActivity(intent);
                break;
        }
    }
}
