package com.example.carlos.servicioweb;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase generada por Carlos Garrido para la aplicación ComandUal en 08/10/2016
 */

/*http://www.parcelabler.com/*/
public class Trick implements Parcelable {
    private String _id;
    private String name;
    private String description;
    private String slowMotion;
    private String tutorial;
    private String category;


    public Trick(String _id, String name, String description, String slowMotion, String tutorial, String category) {
        this.name = name;
        this.description = description;
        this.slowMotion = slowMotion;
        this.tutorial = tutorial;
        this.category = category;
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlowMotion() {
        return slowMotion;
    }

    public void setSlowMotion(String slowMotion) {
        this.slowMotion = slowMotion;
    }

    public String getTutorial() {
        return tutorial;
    }

    public void setTutorial(String tutorial) {
        this.tutorial = tutorial;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Trick{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", slowMotion='" + slowMotion + '\'' +
                ", tutorial='" + tutorial + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    protected Trick(Parcel in) {
        _id = in.readString();
        name = in.readString();
        description = in.readString();
        slowMotion = in.readString();
        tutorial = in.readString();
        category = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(slowMotion);
        dest.writeString(tutorial);
        dest.writeString(category);
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