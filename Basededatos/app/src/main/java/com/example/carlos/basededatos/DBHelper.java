package com.example.carlos.basededatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase generada por Carlos Garrido para la aplicación ComandUal en 20/09/2016
 */
public class DBHelper extends SQLiteOpenHelper{

    String sqlCreate = "CREATE TABLE Alumnos (codigo INTEGER, nombre TEXT)";
    String sqlCreate2 = "CREATE TABLE Alumnos (codigo INTEGER, nombre TEXT, edad INTEGER)";
    String sqlDrop = "DROP TABLE IF EXISTS Alumnos";

    private static final String dbname = "alumnos.sqlite";
    private static final int dbversion = 1;

    public DBHelper(Context context) {
        super(context, dbname, null, dbversion);

    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(sqlDrop);
        db.execSQL(sqlCreate2);
    }
}
