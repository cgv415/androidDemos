package com.example.carlos.apilogin;

import android.app.Application;
import android.content.Context;
/**
 * Clase generada por Carlos Garrido para la aplicación ComandUal en 03/10/2016
 */
public class MyApplication extends Application{
    private static MyApplication mInstance;
    private static Context mAppContext;

    @Override
    public  void onCreate(){
        super.onCreate();
        mInstance = this;
        this.setAppContext(getApplicationContext());
    }

    public static MyApplication getInstance(){
        return mInstance;
    }

    public static Context getAppContext(){
        return mAppContext;
    }

    public void setAppContext(Context mAppContext){
        this.mAppContext = mAppContext;
    }

}
