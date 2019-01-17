package com.example.carlos.basededatos;

import java.util.ArrayList;

/**
 * Clase generada por Carlos Garrido para la aplicación ComandUal en 20/09/2016
 */
public interface DataBase {
    public void eliminarTabla(String nombreTabla);

    /*Alumnos*/
    public void crearTablaAlumno();
    public boolean insertarAlumno(int codigo, String nombre,String curso);
    public boolean modificarAlumno(int codigo, String nuevoNombre, String curso);
    public boolean eliminarAlumno(int codigo);
    public boolean eliminarAlumno(String nombre);
    public ArrayList<ArrayList<String>> obtenerAlumnos();
    public ArrayList<String> obtenerAlumno(int codigo);
    public ArrayList<ArrayList<String>> obtenerAlumno(String nombre);
    public ArrayList<ArrayList<String>> obtenerAlumnoPorCursos(String curso);

}
