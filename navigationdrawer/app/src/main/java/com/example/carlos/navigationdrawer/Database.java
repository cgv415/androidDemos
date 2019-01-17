package com.example.carlos.navigationdrawer;

import java.util.ArrayList;

/**
 * Created by Carlos on 31/01/2016.
 */
public interface Database {
    public void eliminarTabla(String nombreTabla);

    /*Productos*/
    public void crearTablaProducto();
    public boolean insertarProducto(String nombre, String descripcion, String categoria, String tipo, String precio, String stock);
    public boolean modificarProducto(String nombre, String descripcion, String categoria, String tipo, String precio, String stock);
    public ArrayList<ArrayList<String>> obtenerProductos();
    public ArrayList<ArrayList<String>> buscarProducto(String nombre);
    public boolean eliminarProducto(String nombre);


    /*Empleados*/
    public void crearTablaEmpleados();
    public boolean insertarEmpleado(String nombre, String apellido, String puesto, String correo, String nick, String password, String remember);
    public boolean modificarEmpleado(String nombre, String apellido, String puesto, String correo, String nick, String password, String remember);
    public ArrayList<ArrayList<String>> obtenerEmpleado(String nick);
    public ArrayList<ArrayList<String>> obtenerEmpleados();
    public boolean eliminarEmpleado(String nick);


    /*Mesa*/
    public void crearTablaMesa();
    public boolean insertarMesa(String capacidad, String forma, String reservado, String espacio);
    public boolean modificarMesa(String capacidad, String forma, String reservado, String espacio);
    public ArrayList<ArrayList<String>> buscarMesa(String codigo);
    public ArrayList<ArrayList<String>> buscarMesaPorEspacio(String espacio);
    public ArrayList<ArrayList<String>> obtenerMesas();
    public boolean eliminarMesa(String capacidad);


    /*Espacio*/
    public void crearTablaEspacio();
    public boolean insertarEspacio(String espacio);
    public ArrayList<String> obtenerEspacios();
    public boolean eliminarEspacio(String espacio);

    //public void insertarMesa();
}
