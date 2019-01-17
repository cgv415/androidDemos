package com.example.carlos.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Clase generada por Carlos Garrido para la aplicación ComandUal en 08/11/2016
 */
public class MiFirebaseInstanceIdService extends FirebaseInstanceIdService{
    private static final String TAG = "Noticias";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG,"Token: "+token);
    }
}
