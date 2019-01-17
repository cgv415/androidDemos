package com.example.carlos.navigationdrawer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Carlos on 24/01/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "comandas.sqlite";
    private static final int DB_SCHEMA_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.CREATE_TABLE_PERSONAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
