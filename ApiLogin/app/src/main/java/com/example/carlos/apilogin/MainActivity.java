package com.example.carlos.apilogin;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*Aplicacion creada siguiendo el tutorial
    https://www.youtube.com/watch?v=cJGB4TF15AY&list=PLNRxocD70gLoEx4O2pr4Klr3rfvlcAAfs&index=60*/
    private EditText etEmail;
    private EditText etPassword;
    private Button btnIniciar;
    private ProgressBar progreso;

    private JsonObjectRequest array;
    private RequestQueue mrequestQueue;
    private final String url = "http://192.168.0.101:8080/api/trick";
    //private final String url = "http://192.168.0.101:8080/login/login/public/api/v1/iniciarSesion";
    private final String TAG = "PRUEBITA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnIniciar = (Button)findViewById(R.id.btnIniciar);
        progreso = (ProgressBar)findViewById(R.id.progressBar);
        progreso.setVisibility(View.INVISIBLE);

        btnIniciar.setOnClickListener(this);

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
            case R.id.btnIniciar:
                progreso.setVisibility(View.VISIBLE);
                mrequestQueue = VolleySingleton.getInstance().getmRequestQueue();
                StringRequest request = new StringRequest(Request.Method.GET,url,new Response.Listener<String>(){
                    @Override
                public void onResponse(String response){
                        String token = response;
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        progreso.setVisibility(View.INVISIBLE);
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                        progreso.setVisibility(View.INVISIBLE);
                    }
                });
                /*{
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<String,String>();
                        map.put("email",etEmail.getText().toString());
                        map.put("password",etPassword.getText().toString());
                        return map;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("Content-Type","x-www-form-urlencoded");
                        return params;
                    }
                };*/
                mrequestQueue.add(request);
                break;
        }
    }
}
