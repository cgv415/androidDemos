package com.example.garrido.ruta_gps;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.garrido.ruta_gps.directionhelpers.FetchURL;
import com.example.garrido.ruta_gps.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,TaskLoadedCallback {

    private GoogleMap mMap;
    private Polyline currentPolyline;
    private ArrayList<MarkerOptions> mMarkerArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String lat = latLng.toString();
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


        /*mMarkerArray.add(markerMurcia);
        mMarkerArray.add(markerAlicante);
        mMarkerArray.add(markerAlbacete);*/


        mMap.addMarker(markerAlmeria);
        mMap.addMarker(markerGranada);
        mMap.addMarker(markerCadiz);
        mMap.addMarker(markerJerez);
        mMap.addMarker(markerSevilla);
        mMap.addMarker(markerJaen);


        /*mMap.addMarker(markerMurcia);
        mMap.addMarker(markerAlicante);
        mMap.addMarker(markerAlbacete);*/

        mMap.moveCamera(CameraUpdateFactory.newLatLng(almeria));

        //new FetchURL(MapsActivity.this).execute(getUrl(almeria, granada, "driving"), "driving");

        ArrayList<ArrayList<String>> puntos = ordenarDistancias();

        ArrayList<ArrayList<String>> ruta = generarRuta(puntos);

        ruta = ordenarRuta(ruta);
        dibujarRecorrido(ruta);
        String bp = "";
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

    public void dibujarRecorrido(ArrayList<ArrayList<String>> ruta){
        ArrayList<LatLng> points;
        PolylineOptions lineOptions = null;

        lineOptions = new PolylineOptions();

        for (int i = 0; i < ruta.size(); i++) {
            points = new ArrayList<>();
            // Fetching i-th route

            int pos1 = Integer.parseInt(ruta.get(i).get(0));
            int pos2 = Integer.parseInt(ruta.get(i).get(1));
            points.add(mMarkerArray.get(pos1).getPosition());
            points.add(mMarkerArray.get(pos2).getPosition());

            lineOptions.addAll(points);


        }

        lineOptions.width(20);
        lineOptions.color(Color.BLUE);

        TaskLoadedCallback taskCallback;
        taskCallback = (TaskLoadedCallback) this;
        taskCallback.onTaskDone(lineOptions);

        /*if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) lineOptions);*/
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}
