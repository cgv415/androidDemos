package com.example.carlos.navigationdrawer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Carlos on 24/01/2016.
 */
public class DataBaseManager implements Database {

    private DBHelper helper;
    private SQLiteDatabase db;
    public String rol;
    public DataBaseManager(Context context) {

        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public void eliminarTabla(String nombreTabla){
        db.execSQL("Drop table if exists " + nombreTabla);
    }

    public void actualizar(){

        this.crearTablaEmpleados();
        this.generarEmpleados();

        this.crearTablaProducto();
        this.generarProductos();

        this.crearTablaMesa();
        this.generarMesas();

        this.crearTablaEspacio();
        this.generarEspacios();

    }

    public static final String TABLE_PERSONAL = "personal";
    public static final String CN_ID = "_id";
    public static final String CN_NOMBRE = "nombre";
    public static final String CN_APELLIDO = "apellido";
    public static final String CN_PUESTO = "puesto";
    public static final String CN_CORREO = "correo";
    public static final String CN_NICK = "nick";
    public static final String CN_PASSWORD = "password";
    public static final String CN_REMEMBER = "remember";
    public static final String CREATE_TABLE_PERSONAL = "create table " + TABLE_PERSONAL + "("
            + CN_ID + " integer primary key autoincrement,"
            + CN_NOMBRE + " text not null,"
            + CN_APELLIDO + " text not null,"
            + CN_PUESTO + " text not null,"
            + CN_CORREO + " text not null unique,"
            + CN_NICK + " text not null unique,"
            + CN_PASSWORD + " text not null,"
            + CN_REMEMBER + " text not null" +
            ");";

    public int login(String nick, String password, boolean recordar){
        int login = 0;

        String[] columna = new String[] {CN_NICK,CN_PASSWORD};
        Cursor c = db.query(TABLE_PERSONAL,columna,CN_NICK + "= ?",new String[]{nick},null,null,null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            if(!password.equals(c.getString(1))){
                login = 2;
            }
        }else{
            login = 1;
        }

        //Para pruebas
        if(nick.isEmpty()){
            rol = "admin";
            login = 0;
        }

        return login;
    }

    /*PRODUCTOS*/
    public static final String TABLE_PRODUCTO = "producto";
    public static final String CN_DESCRIPCION = "descripcion";
    public static final String CN_CATEGORIA = "categoria";
    public static final String CN_TIPO = "tipo";
    public static final String CN_PRECIO = "precio";
    public static final String CN_STOCK = "stock";
    public static final String CREATE_TABLE_PRODUCTO = "create table " + TABLE_PRODUCTO + "("
            + CN_ID + " integer primary key autoincrement,"
            + CN_NOMBRE + " text not null,"
            + CN_DESCRIPCION + " text,"
            + CN_CATEGORIA + " text not null,"
            + CN_TIPO + " text not null,"
            + CN_PRECIO + " real not null,"
            + CN_STOCK + " text not null"
            + ");";

    public void crearTablaProducto(){
        this.eliminarTabla(TABLE_PRODUCTO);
        db.execSQL(CREATE_TABLE_PRODUCTO);
    }

    public ContentValues generarValoresProducto(String nombre, String descripcion,String categoria, String tipo, String precio, String stock){
        ContentValues valores = new ContentValues();
        valores.put(CN_NOMBRE,nombre);
        valores.put(CN_DESCRIPCION,descripcion);
        valores.put(CN_CATEGORIA, categoria);
        valores.put(CN_TIPO, tipo);
        valores.put(CN_PRECIO, precio);
        valores.put(CN_STOCK, stock);
        return valores;
    }

    public boolean insertarProducto(String nombre, String descripcion,String categoria, String tipo, String precio,String stock) {
        db.insert(TABLE_PRODUCTO, null, generarValoresProducto(nombre, descripcion, categoria, tipo, precio, stock));
        return true;
    }

    @Override
    public boolean modificarProducto(String nombre, String descripcion, String categoria, String tipo, String precio, String stock) {
        return false;
    }

    @Override
    public ArrayList<ArrayList<String>> obtenerProductos() {
        return null;
    }

    @Override
    public ArrayList<ArrayList<String>> buscarProducto(String nombre) {
        return null;
    }

    public boolean eliminarProducto(String nombre) {
        db.delete(TABLE_PRODUCTO, CN_NOMBRE + "=?", new String[]{nombre});
        return true;
    }
    //Para pruebas
    public void generarProductos(){
        insertarProducto("Arroz", "Arroz con habichuelas", "Arroz", "Tapa", "2.0", "1");
        insertarProducto("Carne con tomate", "Carne con tomate", "Carne", "Tapa", "2.0", "1");
        insertarProducto("Merluza", "Merluza", "Pescado", "Racion", "12.0", "1");
        insertarProducto("Alhambra 1925", "", "Cerveza", "Bebida", "2.5", "1");
    }

    /*EMPLEADOS*/

    public ContentValues generarValoresEmpleado(String nombre, String apellido, String puesto, String correo, String nick, String password,String remember){
        ContentValues valores = new ContentValues();
        valores.put(CN_NOMBRE,nombre);
        valores.put(CN_APELLIDO,apellido);
        valores.put(CN_PUESTO,puesto);
        valores.put(CN_CORREO, correo);
        valores.put(CN_NICK, nick);
        valores.put(CN_PASSWORD, password);
        valores.put(CN_REMEMBER, remember);
        return valores;
    }

    public void crearTablaEmpleados(){
        this.eliminarTabla(TABLE_PERSONAL);
        db.execSQL(CREATE_TABLE_PERSONAL);
    }

    public boolean insertarEmpleado(String nombre, String apellido, String puesto, String correo, String nick, String password,String remember){
        db.insert(TABLE_PERSONAL, null, generarValoresEmpleado(nombre, apellido, puesto, correo, nick, password, remember));
        return true;
    }

    @Override
    public boolean modificarEmpleado(String nombre, String apellido, String puesto, String correo, String nick, String password, String remember) {
        return false;
    }

    @Override
    public ArrayList<ArrayList<String>> obtenerEmpleado(String nick) {
        String[] columnas = new String[] {CN_ID,CN_NOMBRE,CN_APELLIDO};
        Cursor c = db.query(TABLE_PERSONAL,columnas,CN_NICK+ " = ?",new String[]{nick},null,null,null);
        return null;
    }

    @Override
    public ArrayList<ArrayList<String>> obtenerEmpleados() {
        String[] columnas = new String[] {CN_ID,CN_NOMBRE,CN_APELLIDO};
        Cursor c = db.query(TABLE_PERSONAL, columnas, null, null, null, null, null);
        return null;
    }

    public boolean eliminarEmpleado(String nick){
        db.delete(TABLE_PERSONAL, CN_NICK + "=?", new String[]{nick});
        return true;
    }
    //Para pruebas
    public void generarEmpleados(){
        insertarEmpleado("Carlos", "Garrido", "Jefe", "carlos@gmail.com", "cgv", "1234", "0");
        insertarEmpleado("Jose", "vergara", "Cocina", "jose@gmail.com", "jgm", "1234","0");
        insertarEmpleado("Juan", "Manzano", "Mesas", "juan@gmail.com", "jagm", "1234","0");
        insertarEmpleado("Admin", "Admin", "Admin", "admin@gmail.com", "admin", "admin", "0");
    }



    /*MESAS*/
    public static final String TABLE_MESA = "mesa";
    public static final String CN_CAPACIDAD = "capacidad";
    public static final String CN_FORMA = "forma";
    public static final String CN_RESERVADO = "reservado";
    public static final String CN_ESPACIO = "espacio";

    public static final String CREATE_TABLE_MESA = "create table " + TABLE_MESA+ "("
            + CN_ID + " integer primary key autoincrement,"
            + CN_CAPACIDAD + " integer not null,"
            + CN_FORMA + " text not null,"
            + CN_RESERVADO + " text not null,"
            + CN_ESPACIO + " text not null"
            + ");";
    @Override
    public void crearTablaMesa() {
        this.eliminarTabla(TABLE_MESA);
        //Log.d("Crear mesa ",CREATE_TABLE_MESA); Remove
        db.execSQL(CREATE_TABLE_MESA);
    }

    @Override
    public boolean insertarMesa(String capacidad,String forma,String reservado,String espacio) {
        db.insert(TABLE_MESA, null, generarValoresMesa(capacidad, forma, reservado, espacio));
        return true;
    }

    @Override
    public boolean modificarMesa(String capacidad, String forma, String reservado, String espacio) {
        return false;
    }

    @Override
    public ArrayList<ArrayList<String>> buscarMesa(String codigo) {
        ArrayList<String> tabla = new ArrayList<String>();
        ArrayList<String> columna = new ArrayList<String>();
        String[] columnas = new String[] {CN_ID,CN_CAPACIDAD,CN_FORMA,CN_RESERVADO,CN_ESPACIO};
        Cursor cursor = db.query(TABLE_MESA,columnas,CN_ID+ " = ?",new String[]{codigo},null,null,null);
        if(cursor.moveToFirst()) {
            do {
                columna.add("" + cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        return null;
    }

    @Override
    public ArrayList<ArrayList<String>> buscarMesaPorEspacio(String espacio) {
        ArrayList<ArrayList<String>> tabla = new ArrayList<ArrayList<String>>();
        ArrayList<String> columna = new ArrayList<String>();
        String[] columnas = new String[] {CN_ID,CN_CAPACIDAD,CN_FORMA,CN_RESERVADO,CN_ESPACIO};
        Cursor cursor = db.query(TABLE_MESA,columnas,CN_ESPACIO+ " = ?",new String[]{espacio},null,null,null);
        if(cursor.moveToFirst()) {
            do {
                columna = new ArrayList<String>();
                columna.add("" + cursor.getInt(0));
                columna.add("" + cursor.getInt(1));
                columna.add("" + cursor.getString(2));
                columna.add("" + cursor.getInt(3));
                columna.add("" + cursor.getString(4));
                //Log.d(""+cursor.getInt(0),columna.toString());//remove
                tabla.add(columna);
            } while (cursor.moveToNext());
        }
        return tabla;
    }

    @Override
    public ArrayList<ArrayList<String>> obtenerMesas() {
        String[] columnas = new String[] {CN_ID,CN_CAPACIDAD,CN_FORMA,CN_RESERVADO,CN_ESPACIO};
        ArrayList<ArrayList<String>> tabla = new ArrayList<ArrayList<String>>();
        ArrayList<String> columna = new ArrayList<String>();
        Cursor cursor = db.query(TABLE_MESA,columnas,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {
                columna = new ArrayList<String>();
                columna.add("" + cursor.getInt(0));
                columna.add("" + cursor.getInt(1));
                columna.add("" + cursor.getString(2));
                columna.add("" + cursor.getInt(3));
                columna.add("" + cursor.getString(4));
                //Log.d(""+cursor.getInt(0),columna.toString());//remove
                tabla.add(columna);

            } while (cursor.moveToNext());
        }
        return tabla;
    }

    @Override
    public boolean eliminarMesa(String codigo) {
        return false;
    }
    private ContentValues generarValoresMesa(String capacidad,String forma,String reservado,String espacio) {
        ContentValues valores = new ContentValues();
        valores.put(CN_CAPACIDAD,capacidad);
        valores.put(CN_FORMA,forma);
        valores.put(CN_RESERVADO, reservado);
        valores.put(CN_ESPACIO,espacio);

        return valores;
    }

    //Para pruebas
    public void generarMesas(){
        insertarMesa("4", "cuadrado","0","comedor");//insertarMesa(codigo,capacidad, forma,x,y,resrvado,lugar,posX,posY);
        insertarMesa("6","cuadrado","0","comedor");
        insertarMesa("6","cuadrado","0","comedor");
        insertarMesa("8","redonda","0","comedor");

    }


    /*Espacios*/
    public static final String TABLE_ESPACIO = "espacio";
    public static final String CREATE_TABLE_ESPACIO = "create table " + TABLE_ESPACIO + "("
            + CN_ID + " integer primary key autoincrement,"
            + CN_ESPACIO + " text not null"
            + ");";

    @Override
    public void crearTablaEspacio() {
        this.eliminarTabla(TABLE_ESPACIO);
        db.execSQL(CREATE_TABLE_ESPACIO);
    }

    @Override
    public boolean insertarEspacio(String espacio) {
        db.insert(TABLE_ESPACIO, null, generarValoresEspacio(espacio));
        return true;
    }

    @Override
    public ArrayList<String> obtenerEspacios() {
        String[] columnas = new String[] {CN_ID,CN_ESPACIO};
        ArrayList<String> tabla = new ArrayList<String>();
        Cursor cursor = db.query(TABLE_ESPACIO,columnas,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {
                tabla.add(cursor.getString(1));
                //Log.d("Espacio",cursor.getString(1));//Remove
            } while (cursor.moveToNext());
        }
        return tabla;
    }

    @Override
    public boolean eliminarEspacio(String espacio) {
        return false;
    }

    private ContentValues generarValoresEspacio(String espacio) {
        ContentValues valores = new ContentValues();
        valores.put(CN_ESPACIO,espacio);

        return valores;
    }
    //Para pruebas
    public void generarEspacios(){
        insertarEspacio("comedor");//insertarMesa(codigo,capacidad, forma,x,y,resrvado,lugar,posX,posY);
        insertarEspacio("terraza");
        insertarEspacio("barra");

    }
}
