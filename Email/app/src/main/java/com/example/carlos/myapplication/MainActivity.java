package com.example.carlos.myapplication;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button enviarEmail;
    private EditText cuerpo;
    private EditText asunto;
    private EditText destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        destino =(EditText) findViewById(R.id.destinatario);
        asunto =(EditText) findViewById(R.id.asunto);
        cuerpo =(EditText) findViewById(R.id.cuerpo);
        enviarEmail = (Button) findViewById(R.id.enviar);
        enviarEmail.setOnClickListener(this);

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
            case R.id.enviar:
                enviarMail();
                break;
        }
    }

    public void enviarMail(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{destino.getText().toString()});
        intent.putExtra(Intent.EXTRA_SUBJECT,asunto.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT,cuerpo.getText().toString());
        try {
            startActivity(intent.createChooser(intent,"Enviando correo"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
