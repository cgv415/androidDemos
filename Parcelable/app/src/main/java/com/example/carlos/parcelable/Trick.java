package com.example.carlos.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase generada por Carlos Garrido para la aplicación ComandUal en 10/10/2016
 */
public class Trick implements Parcelable {
    private String nombre;
    private String dificultad;

    public Trick(String nombre, String dificultad) {
        this.nombre = nombre;
        this.dificultad = dificultad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    @Override
    public String toString() {
        return "Trick{" +
                "nombre='" + nombre + '\'' +
                ", dificultad='" + dificultad + '\'' +
                '}';
    }

    protected Trick(Parcel in) {
        nombre = in.readString();
        dificultad = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(dificultad);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Trick> CREATOR = new Parcelable.Creator<Trick>() {
        @Override
        public Trick createFromParcel(Parcel in) {
            return new Trick(in);
        }

        @Override
        public Trick[] newArray(int size) {
            return new Trick[size];
        }
    };
}