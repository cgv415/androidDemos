package com.example.carlos.servicioweb;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
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
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.BufferOverflowException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button consultar;

    //URLs
    private String id_trick = "/57f63571f78f4f1d7ca43c01";
    private String IP = "http://192.168.0.101:8080";
    private final String GET = IP+"/api/trick";
    private final String url = "http://192.168.0.101:8080/api/trick";
    private String GET_BY_ID = GET+id_trick;

    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        consultar = (Button) findViewById(R.id.btnConsultar);
        consultar.setOnClickListener(this);
        consultar.performClick();
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
            case R.id.btnConsultar:

                mRequestQueue = VolleySingleton.getInstance().getmRequestQueue();
                //Si devuelve exception pointer null, acuerdate de
                // agregar en Android manifest android:name=".MyApplication""
                StringRequest request = new StringRequest(Request.Method.GET,this.GET_BY_ID,new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject obj = new JSONObject(response);
                            String name = obj.getString("trick");
                            obj = new JSONObject(obj.getString("trick"));

                            Trick trick = new Trick(obj.getString("_id"),obj.getString("name"),obj.getString("description"),obj.getString("slowMotion"),obj.getString("tutorial"),obj.getString("category"));
                            //String name = obj.getString("name");

                            Log.d("My app","Vas bien, animo!");
                        }catch (Throwable t){
                            Log.e("My app","could not parse malformed JSON: ");
                        }
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
                mRequestQueue.add(request);
                break;
        }
    }
}



