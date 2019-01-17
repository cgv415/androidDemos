package com.example.carlos.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Clase generada por Carlos Garrido para la aplicación ComandUal en 08/11/2016
 */
public class MiFirebaseMessagingService extends FirebaseMessagingService{

    private static final String TAG = "Noticias";
    //Token: eccpyuBjEeU:APA91bHXI4yVHGC8UlBtOzmP4aI7mcnhRT7Syfup9FNdvxXEeOcojXCTYLkxPCbZkc2wKcSCHNQS2q7wyhxKy6DtWz9EBrl58CbT1Yis4mWByfgabbT5GMHEDEU0R8-JWRQZliB_r4eB

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.d(TAG,"Mensaje recibido de: "+from);

        if(remoteMessage.getNotification() !=null){
            Log.d(TAG,"Notificacion: " + remoteMessage.getNotification().getBody());

            mostrarNotificacion(remoteMessage);
        }

        if (remoteMessage.getData().size()>0){
            Log.d(TAG,"Data: " + remoteMessage.getData());
        }
    }

    private void mostrarNotificacion(RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, Main2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_event_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
    }


}
