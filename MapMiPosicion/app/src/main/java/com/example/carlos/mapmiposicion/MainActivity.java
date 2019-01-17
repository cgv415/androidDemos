package com.example.carlos.mapmiposicion;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/*https://www.youtube.com/watch?v=EIGSIxJP6Zs*/
public class MainActivity extends ActionBarActivity implements LocationListener {

    private Switch mswitch;
    private EditText etLongitud;
    private EditText etLatitud;
    private EditText etDireccion;
    private TextView tvGps;

    public LocationManager handle;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mswitch = (Switch) findViewById(R.id.switchUbicacion);
        etLongitud = (EditText) findViewById(R.id.etLongitud);
        etLatitud = (EditText) findViewById(R.id.etLatitud);
        etDireccion = (EditText) findViewById(R.id.etDireccion);
        tvGps = (TextView) findViewById(R.id.tvProveedor);

        mswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEstadoSwitch(isChecked);
            }
        });

    }

    public void setEstadoSwitch(boolean x){
        if(x){
            iniciarServicio();
            mostrarPosicionActual();
        }else{
            pararServicio();
        }
    }

    public void checkPermission(){
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
    }

    public void iniciarServicio(){
        Toast.makeText(this,"Serivicio activado",Toast.LENGTH_SHORT).show();
        handle = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        provider = handle.getBestProvider(c,true);

        tvGps.setText("Proveedor: "+provider);
        checkPermission();
        handle.requestLocationUpdates(provider,1000,1,this);
    }

    public void mostrarPosicionActual(){
        checkPermission();
        Location location = handle.getLastKnownLocation(provider);
        if(location==null){
            etLongitud.setText("Desconocida");
            etLatitud.setText("Desconocida");
        }else{
            etLongitud.setText(String.valueOf(location.getLongitude()));
            etLatitud.setText(String.valueOf(location.getLatitude()));
        }
        //Mostrar direccion
        setDireccion(location);
    }

    public void setDireccion(Location location){
        if(location!=null){
            if(location.getLongitude()!=0.0 && location.getLatitude()!=0.0){
                try {
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> list = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if(!list.isEmpty()){
                        Address direccion = list.get(0);
                        etDireccion.setText(direccion.getAddressLine(0));
                    }
                }catch (IOException e){
                    etDireccion.setText(""+ e);
                }
            }
        }
    }

    public void pararServicio(){
        checkPermission();
        handle.removeUpdates(this);
        etDireccion.setText(null);
        etLongitud.setText(null);
        etLatitud.setText(null);
        Toast.makeText(this,"Serivicio parado",Toast.LENGTH_SHORT).show();
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
    public void onLocationChanged(Location location) {

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
}
