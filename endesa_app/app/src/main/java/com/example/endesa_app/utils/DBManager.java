package com.example.endesa_app.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import com.example.endesa_app.models.*;

public class DBManager {


    private static DBHelper helper;
    public static SQLiteDatabase db;
    //public static Account user;

    public DBManager(Context ctx){
        helper = new DBHelper(ctx);
        db=helper.getWritableDatabase();
    }

    public static void instance(Context ctx){
        helper = new DBHelper(ctx);
        db=helper.getWritableDatabase();
    }

    public static void checkDb(){
        //table no se usa, pero es necesario para poder hacer condiciones ternarias.
        boolean table;
        table = tableExists(Account.table)?true:Account.createTable();
        table = tableExists(Challenge.table)?true:Challenge.createTable();
        table = tableExists(About.table)?true:About.createTable();
        table = tableExists(Privacy.table)?true:Privacy.createTable();
        table = tableExists(Steps.table)?true:Steps.createTable();
    }

    public static boolean tableExists(String tableName)
    {
        if (tableName == null || db == null || !db.isOpen())
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public static void deleteDB(){
        Account.deleteTable();
        Challenge.deleteTable();
        About.deleteTable();
        Privacy.deleteTable();
        Steps.deleteTable();
    }

    public static void restartDB(){
        Account.createTable();
        Challenge.createTable();
        About.createTable();
        Privacy.createTable();
        Steps.createTable();
    }

    public static boolean createTable(String create){
       db.execSQL(create);
       return true;
    }

    public static void deleteTable(String nombreTabla){
        db.execSQL("Drop table if exists " + nombreTabla);
    }

    public static void close(){
        db.close();
    }


    public static long insertWithoutDate(String table, ContentValues values){
        long id = db.insert(table,null, values);
        return id;
    }

    public static long insert(String table, ContentValues values){
        values.put("created_at",AppManager.getDateTime());
        values.put("updated_at",AppManager.getDateTime());
        long id = db.insert(table,null, values);
        return id;
    }

    public static boolean updateById(String table, ContentValues values,String id) {
        String[] whereClause = new String[]{"id"};
        String[] whereArgs = new String[]{id};
        boolean updated = update(table,values,whereClause,whereArgs);
        return updated;
    }

    public static boolean updateWithoutDate(String table, ContentValues values,String[] whereClauses,String[] whereArgs) {
        String whereClause = parseToSelection(whereClauses);
        int result = db.update(table, values, whereClause,whereArgs );
        return result == 1;
    }

    public static boolean update(String table, ContentValues values,String[] whereClauses,String[] whereArgs) {
        values.put("updated_at",AppManager.getDateTime());
        String whereClause = parseToSelection(whereClauses);
        int result = db.update(table, values, whereClause,whereArgs );
        return result == 1;
    }

    public static boolean deleteById(String table,String id) {
        String[] whereClause = new String[]{"id"};
        String[] whereArgs = new String[]{id};
        boolean deleted = delete(table,whereClause,whereArgs);
        return deleted;
    }

    public static boolean delete(String table,String[] whereClauses,String[] whereArgs) {
        String whereClause = parseToSelection(whereClauses);
        db.delete(table, whereClause, whereArgs);
        return true;
    }

    public static ArrayList<ArrayList<String>> query(String table, String[] columns, String selections, String[] selectionArgs, String orderBy){
        ArrayList<ArrayList<String>> objects = new ArrayList<>();
        ArrayList<String> object;

        Cursor cursor = db.query(table, columns, selections, selectionArgs, null, null, orderBy);
        if(cursor.moveToFirst()){
            do{
                object = new ArrayList<>();

                for(int i = 0; i < cursor.getColumnCount(); i++){
                    object.add(cursor.getString(i));
                }

                objects.add(object);

            }while(cursor.moveToNext());
        }

        cursor.close();

        return objects;
    }

    public static ArrayList<ArrayList<String>> where(String table, String[] columns, String[] selections, String[] selectionArgs, String orderBy){
        String selection = parseToSelection(selections);

        ArrayList<ArrayList<String>> objects = new ArrayList<>();
        ArrayList<String> object;

        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, orderBy);
        if(cursor.moveToFirst()){
            do{
                object = new ArrayList<>();

                for(int i = 0; i < cursor.getColumnCount(); i++){
                    object.add(cursor.getString(i));
                }

                objects.add(object);

            }while(cursor.moveToNext());
        }

        cursor.close();

        return objects;
    }

    public static ArrayList<ArrayList<String>> where(String table, String[] columns, String[] selections, String[] selectionArgs) {
        return where(table,columns,selections,selectionArgs,null);
    }

    public static ArrayList<ArrayList<String>> where(String table, String[] columns, String selection, String selectionArgs) {
        return where(table,columns,new String[]{selection},new String[]{selectionArgs},null);
    }

    public static ArrayList<ArrayList<String>> all(String table, String[] columns) {
        return where(table,columns,null,null,null);
    }

    public static ArrayList<String> findById(String table, String[] columns, String id){
        return find(table,columns,new String[]{"id"},new String[]{id},null);
    }

    public static ArrayList<String> find(String table, String[] columns, String[] selections, String[] selectionArgs){
        return find(table,columns,selections,selectionArgs,null);
    }

    public static ArrayList<String> find(String table, String[] columns, String[] selections, String[] selectionArgs,String orderBy){
        String selection = parseToSelection(selections);

        ArrayList<String> object = new ArrayList<>();

        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);
        if(cursor.moveToFirst()){
            for(int i = 0; i < cursor.getColumnCount(); i++){
                object.add(cursor.getString(i));
            }
        }
        cursor.close();
        return object;
    }

    public static boolean exists(String table, String value){
        ArrayList<String> object = findById(table,new String[]{"id"},value);

        return object.size()>0?true:false;
    }

    public static String parseToSelection(String[] selections){
        if(selections == null){
            return null;
        }

        String selection = "";
        for(int i = 0; i <selections.length; i++){
            selection += selections[i] + " = ?";
            if(i < selections.length -1){
                selection += " AND ";
            }
        }
        return selection;
    }

}
