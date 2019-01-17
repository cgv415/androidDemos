package com.example.garrido.rutasgpsgoogle;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MarkerOptions> mMarkerArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=Paris,France&destination=Cherbourg,France&travelmode=driving&waypoints=Versailles,France%7CChartres,France%7CLe+Mans,France%7CCaen,France");
                //Uri gmmIntentUri = Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        // Add a marker in Sydney and move the camera
        LatLng almeria = new LatLng(36.835733185746854, -2.466874234378338);
        LatLng granada = new LatLng(37.178194507102766, -3.5982504487037654);
        LatLng cadiz = new LatLng(36.52758282289855, -6.28857247531414);
        LatLng jerez = new LatLng(36.684856110354865, -6.126327328383923);
        LatLng sevilla = new LatLng(37.389843440466116, -5.984798222780228);
        LatLng jaen = new LatLng(37.77963415059402, -3.7848349660634995);

        LatLng murcia = new LatLng(37.99191952803689, -1.131475456058979);
        LatLng alicante = new LatLng(38.34594214290071, -0.4902719333767891);
        LatLng albacete = new LatLng(38.99469385550736, -1.8580778315663335);



        MarkerOptions markerAlmeria = new MarkerOptions().position(almeria).title("Marker in Almeria");
        MarkerOptions markerGranada = new MarkerOptions().position(granada).title("Marker in Granada");
        MarkerOptions markerCadiz = new MarkerOptions().position(cadiz).title("Marker in Cadiz");
        MarkerOptions markerJerez = new MarkerOptions().position(jerez).title("Marker in Jerez");
        MarkerOptions markerSevilla = new MarkerOptions().position(sevilla).title("Marker in Sevilla");
        MarkerOptions markerJaen = new MarkerOptions().position(jaen).title("Marker in Jaen");

        /*MarkerOptions markerMurcia = new MarkerOptions().position(murcia).title("Marker in Murcia");
        MarkerOptions markerAlicante = new MarkerOptions().position(alicante).title("Marker in Alicante");
        MarkerOptions markerAlbacete = new MarkerOptions().position(albacete).title("Marker in Albacete");*/



        mMarkerArray.add(markerAlmeria);
        mMarkerArray.add(markerGranada);
        mMarkerArray.add(markerJaen);
        mMarkerArray.add(markerSevilla);
        mMarkerArray.add(markerJerez);
        mMarkerArray.add(markerCadiz);

        ArrayList<ArrayList<String>> puntos = ordenarDistancias();

        ArrayList<ArrayList<String>> ruta = generarRuta(puntos);

        ruta = ordenarRuta(ruta);

        ArrayList<ArrayList<String>> rutaOrdenada = ordenarRecorrido(ruta);

        //rutaOrdenada.remove(rutaOrdenada.size()-1);

        String url = getURL(rutaOrdenada);

        Uri gmmIntentUri = Uri.parse(url);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public ArrayList<ArrayList<String>> ordenarDistancias(){
        double [][] coordenadas = generarCoordenadas();
        double [][] distancia = new double[mMarkerArray.size()][mMarkerArray.size()];

        for(int i = 0; i < mMarkerArray.size(); i++){
            double latOrigen = coordenadas[i][0];
            double lonOrigen = coordenadas[i][1];

            for(int j = 0; j < mMarkerArray.size(); j++){
                if(i==j){
                    distancia[i][j] = 0;
                }else{
                    double latDest = coordenadas[j][0];
                    double lonDest = coordenadas[j][1];

                    double aux = Math.sqrt((Math.pow((latDest-latOrigen),2)+Math.pow((lonDest-lonOrigen),2)));
                    distancia[i][j] = aux;
                }

            }
        }
        ArrayList<String> sol;
        ArrayList<Double> distancias = new ArrayList<>();
        ArrayList<ArrayList<String>> puntos = new ArrayList<>();

        do{
            sol = getMinimo(distancia);
            int i = Integer.parseInt(sol.get(0));
            int j = Integer.parseInt(sol.get(1));

            ArrayList<String> punto = new ArrayList<>();
            if(Double.parseDouble(sol.get(2))!=0.0){
                punto.add(i+"");
                punto.add(j+"");
                puntos.add(punto);
                distancias.add(Double.parseDouble(sol.get(2)));
            }

        }while(Double.parseDouble(sol.get(2))!=0.0);

        return puntos;
    }

    public ArrayList<String> getMinimo(double[][] distancia){

        double distanciamenor;
        ArrayList<String> puntoMinimo = new ArrayList<>();
        distanciamenor = 0.0;

        int lat=0;
        int lon=0;

        for(int i = 0; i < mMarkerArray.size(); i++){
            for(int j = 0; j < mMarkerArray.size(); j++){

                double aux = distancia[i][j];

                if(distanciamenor==0.0){
                    distanciamenor = aux;
                    puntoMinimo.clear();
                    puntoMinimo.add(String.valueOf(i));
                    puntoMinimo.add(String.valueOf(j));
                    puntoMinimo.add(String.valueOf(distanciamenor));

                    lat = i;
                    lon = j;

                }else if(aux !=0.0 && aux < distanciamenor){
                    distanciamenor = aux;
                    lat = i;
                    lon = j;

                    puntoMinimo.clear();
                    puntoMinimo.add(String.valueOf(i));
                    puntoMinimo.add(String.valueOf(j));
                    puntoMinimo.add(String.valueOf(distanciamenor));

                }
            }
        }
        distancia[lat][lon]= 0.0;
        distancia[lon][lat]= 0.0;
        return puntoMinimo;
    }

    public double[][] generarCoordenadas(){
        double[][] coordenadas = new double[mMarkerArray.size()][2];
        for(int i = 0; i < mMarkerArray.size(); i++){
            coordenadas[i][0] = mMarkerArray.get(i).getPosition().latitude;
            coordenadas[i][1] = mMarkerArray.get(i).getPosition().longitude;
        }
        return coordenadas;
    }

    public ArrayList<ArrayList<String>> generarRuta(ArrayList<ArrayList<String>> puntos){
        ArrayList<ArrayList<String>> ruta = new ArrayList<>();
        ArrayList<ArrayList<String>> puntosClone = (ArrayList<ArrayList<String>>) puntos.clone();

        while(puntosClone.size()>0){
            ArrayList<String> punto = puntosClone.get(0);
            if(!rompeRuta(punto,ruta)){
                ruta.add(punto);
                puntosClone.remove(punto);
            }else{
                puntosClone.remove(punto);
            }
        }



        ArrayList<String> cierre = cerrarRuta(ruta,puntos);
        ruta.add(cierre);

        return ruta;
    }

    public ArrayList<ArrayList<String>> ordenarRuta(ArrayList<ArrayList<String>> ruta){
        ArrayList<ArrayList<String>> rutaOrdenada = new ArrayList<>();
        ArrayList<ArrayList<String>> puntosSinColocar = (ArrayList<ArrayList<String>>) ruta.clone();

        rutaOrdenada.add(puntosSinColocar.get(0));
        puntosSinColocar.remove(0);

        while(puntosSinColocar.size()>0){
            ArrayList<String> punto = rutaOrdenada.get(rutaOrdenada.size()-1);
            int lon = Integer.parseInt(punto.get(1));
            for(int i = 0 ; i < puntosSinColocar.size() ; i++){
                ArrayList<String> siguientePunto = puntosSinColocar.get(i);
                int lat = Integer.parseInt(siguientePunto.get(0));
                if(lat == lon){
                    rutaOrdenada.add(siguientePunto);
                    puntosSinColocar.remove(siguientePunto);
                }
            }
        }

        return rutaOrdenada;
    }

    public boolean rompeRuta(ArrayList<String> punto,ArrayList<ArrayList<String>> ruta){
        for(int i = 0 ; i < ruta.size() ; i ++){
            ArrayList<String> r = ruta.get(i);
            if(r.get(0).equals(punto.get(0))){
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> cerrarRuta(ArrayList<ArrayList<String>> ruta,ArrayList<ArrayList<String>> puntos){
        ArrayList<String> cierre = new ArrayList<>();
        obtenerLatitud:
        for(int i = 0 ; i < ruta.size() ; i++){
            boolean conector = false;
            int lat = Integer.parseInt(ruta.get(i).get(1));
            conexion:
            for(int j = i+1; j < ruta.size() ; j++){
                int lon = Integer.parseInt(ruta.get(j).get(0));
                if(lat == lon){
                    conector = true;
                    break conexion;
                }
            }
            if(!conector){
                cierre.add(String.valueOf(lat));
                break obtenerLatitud;
            }
        }

        obtenerLongitud:
        for(int i = 0 ; i < ruta.size() ; i++){
            boolean conector = false;
            int lon = Integer.parseInt(ruta.get(i).get(0));
            conexion:
            for(int j = i+1; j < ruta.size() ; j++){
                int lat = Integer.parseInt(ruta.get(j).get(1));
                if(lon == lat){
                    conector = true;
                    break conexion;
                }
            }
            if(!conector){
                cierre.add(String.valueOf(lon));
                break obtenerLongitud;
            }
        }

        return cierre;
    }

    public ArrayList<ArrayList<String>> ordenarRecorrido(ArrayList<ArrayList<String>> ruta){

        ArrayList<ArrayList<String>> coordenadasRuta = new ArrayList<>();
        ArrayList<String> point;

        for (int i = 0; i < ruta.size(); i++) {
            point = new ArrayList<>();

            int pos1 = Integer.parseInt(ruta.get(i).get(0));
            if(i==0){
                point.add(String.valueOf(mMarkerArray.get(pos1).getPosition().latitude));
                point.add(String.valueOf(mMarkerArray.get(pos1).getPosition().longitude));

                coordenadasRuta.add(point);
                point = new ArrayList<>();
                int pos2 = Integer.parseInt(ruta.get(i).get(1));

                point.add(String.valueOf(mMarkerArray.get(pos2).getPosition().latitude));
                point.add(String.valueOf(mMarkerArray.get(pos2).getPosition().longitude));
                coordenadasRuta.add(point);
            }else{
                int pos2 = Integer.parseInt(ruta.get(i).get(1));

                point.add(String.valueOf(mMarkerArray.get(pos2).getPosition().latitude));
                point.add(String.valueOf(mMarkerArray.get(pos2).getPosition().longitude));
                coordenadasRuta.add(point);
            }
        }
        return coordenadasRuta;
    }

    public String getURL(ArrayList<ArrayList<String>> rutaCoordenadas){
        String origin = "&origin=";
        String destination = "&destination=";
        String travelmode = "&travelmode=driving";
        String waypoints = "&waypoints=";

        for(int i = 0 ; i < rutaCoordenadas.size() ; i++){
            if(i == 0){
                origin += rutaCoordenadas.get(i).get(0) + "," + rutaCoordenadas.get(i).get(1);
            }else if(i == rutaCoordenadas.size()-1){
                destination += rutaCoordenadas.get(i).get(0) + "," + rutaCoordenadas.get(i).get(1);
            }else{
                waypoints+= rutaCoordenadas.get(i).get(0) + "," + rutaCoordenadas.get(i).get(1);
                if(i < rutaCoordenadas.size()-2){
                    waypoints+="%7C";
                }
            }
        }
        String url = "https://www.google.com/maps/dir/?api=1" + origin + destination + travelmode + waypoints;

        return url;
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
}
