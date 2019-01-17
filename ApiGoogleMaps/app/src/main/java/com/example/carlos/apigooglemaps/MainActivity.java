package com.example.carlos.apigooglemaps;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import java.util.List;

/*https://www.youtube.com/watch?v=pIWjkmZpQi4&index=36&list=PLoq0YHvpcFc4hNDgg1bRU91GH35CPjcju*/
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText longitud, latitud;
    private TextView datos;
    private Button btn;
    private ObtenerWebService hiloConexion;
    private Location location;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private AlertDialog alert = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        longitud = (EditText) findViewById(R.id.etLongitud);
        latitud = (EditText) findViewById(R.id.etLatitud);
        datos = (TextView) findViewById(R.id.tvDatos);
        btn = (Button) findViewById(R.id.btnBuscar);
        btn.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        /*El problema es que desde la version 23 en adelante se necesita comprobar que se hayan aceptado los permisos.
        por otro lado la version minima del proyecto es la 21, por lo cual no se necesita comprobar los permisos*/


        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            Log.i("GPS",": apagado");
            AlertNoGps();
        }else{
            Log.i("GPS",": encendido");
        }

        //Si la version es igual o superior a la 23 se comprueban los permisos
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        } else {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mostrarLocalizacion(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        mostrarLocalizacion(location);

        /*Con eto saco datos de los proveedores
        List<String> listaProveedores = locationManager.getAllProviders();
        LocationProvider proveedor1 = locationManager.getProvider(listaProveedores.get(1));
        int accuracy = proveedor1.getAccuracy();
        boolean tieneAltitud = proveedor1.supportsAltitude();

        //Con esta parte establecemos criterios para buscar un proveedor
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String mejorProveedor = locationManager.getBestProvider(criteria,true);
        */
    }

    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(alert != null)
        {
            alert.dismiss ();
        }
    }

    public void mostrarLocalizacion(Location location){
        if(location!=null){
            hiloConexion = new ObtenerWebService();
            hiloConexion.execute(""+location.getLatitude(),""+location.getLongitude());
        }
        else {
            hiloConexion = new ObtenerWebService();
            hiloConexion.execute("36.835808", "-2.466164");
        }

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
            case R.id.btnBuscar:
                //Si la version es igual o superior a la 23 se comprueban los permisos
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    } else {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                } else {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                //hiloConexion = new ObtenerWebService();
                //hiloConexion.execute(latitud.getText().toString(),longitud.getText().toString());
                break;
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                locationManager.removeUpdates(locationListener);
            }
        } else {
            locationManager.removeUpdates(locationListener);
        }


    }
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }



    }

    public class ObtenerWebService extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            String cadena = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";

            cadena += params[0] + "," + params[1] + "&sensor=false";

            String devuelve = "";
            URL url;
            Log.i("GPS","Hola");
            try{

                StringBuilder result = new StringBuilder();
                url = new URL(cadena); //Url de donde queremos obtener informacion
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexion
                connection.setRequestProperty("User-Agent","Mozilla/5.0 (Linux: Android 1.5: es-Es) Ejempolo HTTP");
                int respuesta = connection.getResponseCode();

                if(respuesta == HttpURLConnection.HTTP_OK){
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while((line = br.readLine())!=null){
                        result.append(line);
                    }


                }
                JSONObject respuestaJSON = new JSONObject(result.toString());
                JSONArray resultJSON = respuestaJSON.getJSONArray("results");

                String direcion = "Sin datos para esa longitud y latitud";

                if(resultJSON.length()>0){
                    direcion = resultJSON.getJSONObject(0).getString("formatted_address");
                }

                devuelve = "Dirección: " + direcion;   // variable de salida que mandaré al onPostExecute para que actualice la UI

            }catch (JSONException | IOException e){
                e.printStackTrace();
            }

            return devuelve;
        }

        @Override
        protected void onPreExecute() {
            datos.setText("");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String aVoid) {
            //super.onPostExecute(aVoid);
            datos.setText(aVoid);
            Log.i("GPS", aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String aVoid) {
            super.onCancelled(aVoid);
        }


    }
}
