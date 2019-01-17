package com.example.carlos.basededatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Clase generada por Carlos Garrido para la aplicación ComandUal en 20/09/2016
 * Aplicion generada con el videotutorial
 * https://www.youtube.com/watch?v=HNYFUcAmnyU&list=PLoq0YHvpcFc4hNDgg1bRU91GH35CPjcju&index=34
 */
public class DataBaseManager  implements DataBase{
    private DBHelper helper;
    private SQLiteDatabase db;

    private static final String TABLE_ALUMNO = "alumno";


    private DataBaseManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        if(!isTableExists(db,TABLE_ALUMNO)){
            this.crearTablaAlumno();
        }
    }

    boolean isTableExists(SQLiteDatabase db, String tableName)
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

    public void eliminarTabla(String nombreTabla){
        db.execSQL("Drop table if exists " + nombreTabla);
    }

    private static final String CN_ID = "_id";
    private static final String CN_NOMBRE = "nombre";
    private static final String CN_CURSO = "curso";
    private static final String CREATE_TABLE_ALUMNO = "create table " + TABLE_ALUMNO + "("
            + CN_ID + " integer primary key autoincrement,"
            + CN_NOMBRE + " text not null,"
            + CN_CURSO + " text not null"
            + ");";

    private ContentValues generarValoresAlumno(int codigo, String nombre, String curso){
        ContentValues valores = new ContentValues();
        valores.put(CN_ID, codigo);
        valores.put(CN_NOMBRE, nombre);
        valores.put(CN_CURSO, curso);
        return valores;
    }

    @Override
    public void crearTablaAlumno() {
        this.eliminarTabla(TABLE_ALUMNO);
        db.execSQL(CREATE_TABLE_ALUMNO);
    }

    @Override
    public boolean insertarAlumno(int codigo, String nombre,String curso) {
        db.insert(TABLE_ALUMNO, null, generarValoresAlumno(codigo,nombre,curso));
        return true;
    }

    @Override
    public boolean modificarAlumno(int codigo, String nuevoNombre, String nuevoCurso) {
        return false;
    }

    @Override
    public boolean eliminarAlumno(int codigo) {

        return false;
    }

    @Override
    public boolean eliminarAlumno(String nombre) {
        db.execSQL("DELETE FROM Alumnos WHERE nombre='" + nombre + " '");
        return false;
    }

    @Override
    public ArrayList<ArrayList<String>> obtenerAlumnos() {
        String[] columnas = new String[] {CN_ID,CN_NOMBRE,CN_CURSO};
        ArrayList<ArrayList<String>> tabla = new ArrayList<>();
        ArrayList<String> columna;

        Cursor cursor = db.query(TABLE_ALUMNO,columnas,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                columna = new ArrayList<>();
                columna.add("" + cursor.getInt(0));
                columna.add(cursor.getString(1));
                columna.add(cursor.getString(2));
                tabla.add(columna);
            }while(cursor.moveToNext());
        }
        return tabla;
    }

    @Override
    public ArrayList<String> obtenerAlumno(int codigo) {
        String[] columnas = new String[] {CN_ID,CN_NOMBRE,CN_CURSO};
        ArrayList<String> columna = new ArrayList<>();

        Cursor cursor = db.query(TABLE_ALUMNO,columnas,CN_ID + "= ?",new String[]{""+codigo},null,null,null);
        if(cursor.moveToFirst()){
            do{
                columna.add("" + cursor.getInt(0));
                columna.add("" + cursor.getInt(1));
                columna.add("" + cursor.getInt(2));

            }while(cursor.moveToNext());
        }
        return columna;
    }

    @Override
    public ArrayList<ArrayList<String>> obtenerAlumno(String nombre) {
        String[] columnas = new String[] {CN_ID,CN_NOMBRE,CN_CURSO};
        ArrayList<ArrayList<String>> tabla = new ArrayList<>();
        ArrayList<String> columna;

        Cursor cursor = db.query(TABLE_ALUMNO,columnas,CN_NOMBRE + "= ?",new String[]{nombre},null,null,null);
        if(cursor.moveToFirst()){
            do{
                columna = new ArrayList<>();
                columna.add("" + cursor.getInt(0));
                columna.add("" + cursor.getInt(1));
                columna.add("" + cursor.getInt(2));
                tabla.add(columna);
            }while(cursor.moveToNext());
        }
        return tabla;
    }

    @Override
    public ArrayList<ArrayList<String>> obtenerAlumnoPorCursos(String curso) {
        String[] columnas = new String[] {CN_ID,CN_NOMBRE,CN_CURSO};
        ArrayList<ArrayList<String>> tabla = new ArrayList<>();
        ArrayList<String> columna;

        Cursor cursor = db.query(TABLE_ALUMNO,columnas,CN_CURSO + "= ?",new String[]{curso},null,null,null);
        if(cursor.moveToFirst()){
            do{
                columna = new ArrayList<>();
                columna.add("" + cursor.getInt(0));
                columna.add("" + cursor.getInt(1));
                columna.add("" + cursor.getInt(2));
                tabla.add(columna);
            }while(cursor.moveToNext());
        }
        return tabla;
    }
}
