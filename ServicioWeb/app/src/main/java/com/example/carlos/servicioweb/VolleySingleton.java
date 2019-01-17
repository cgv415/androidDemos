package com.example.carlos.servicioweb;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Clase generada por Carlos Garrido para la aplicación ComandUal en 07/10/2016
 */
public class VolleySingleton {
    private static VolleySingleton instance = null;
    private RequestQueue mRequestQueue;
    private VolleySingleton(){
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
    }

    public static VolleySingleton getInstance(){
        if(instance==null){
            instance = new VolleySingleton();
        }
        return instance;
    }

    public RequestQueue getmRequestQueue() {return mRequestQueue;}
}
