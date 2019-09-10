package com.example.endesa_app.models;

import android.content.ContentValues;

import com.example.endesa_app.utils.DBManager;

import org.json.JSONObject;

import java.util.ArrayList;

public class Privacy {
    public static String table = "privacy";
    private static String[] columns = new String[]{"id","title", "content"};
    public static final String createTable =
            "create table " + table + " (" +
                    "id" + " integer PRIMARY KEY AUTOINCREMENT," +
                    "title text," +
                    "content text," +
                    "created_at text," +
                    "updated_at text" +
                    ");";

    private String id;
    private String title;
    private String content;

    public Privacy(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Privacy(JSONObject privacy){
        try{
            if(privacy.has("id")){
                this.id = privacy.getString("id");
            }

            if(privacy.has("title")){
                this.title = privacy.getString("title");
            }

            if(privacy.has("content")){
                this.content = privacy.getString("content");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return content;
    }

    public void setBody(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Privacy { " +
                "id : '" + id + '\'' +
                ", title : '" + title + '\'' +
                ", content : '" + content + '\'' +
                " }";
    }

    public static boolean createTable(){
        DBManager.createTable(createTable);

        return true;
    }

    public static boolean deleteTable(){
        DBManager.deleteTable(table);
        return true;
    }

    public boolean save(){
        if(DBManager.exists(table,getId())){
            return update();
        }else{
            return DBManager.insert(table,generateValues()) != -1;
        }
    }

    public boolean update(){
        return DBManager.updateById(table,generateValues(),getId());
    }

    public boolean delete(){
        return DBManager.deleteById(table,getId());
    }

    public static ArrayList<Object> all(){
        ArrayList<Object> objects = new ArrayList<>();
        ArrayList<ArrayList<String>> list = DBManager.all(table,columns);

        for(int i = 0; i < list.size(); i++){
            ArrayList<String> values = list.get(i);
            Object object = convertToClass(values);

            objects.add(object);
        }

        return objects;
    }

    public static ArrayList<Object> where(String selection, String selectionArg){
        return where(new String[]{selection},new String[]{selectionArg});
    }

    public static ArrayList<Object> where(String[] selections, String[] selectionArgs){
        ArrayList<Object> objects = new ArrayList<>();
        ArrayList<ArrayList<String>> list = DBManager.where(table,columns,selections,selectionArgs);

        for(int i = 0; i < list.size(); i++){
            ArrayList<String> values = list.get(i);
            Object object = convertToClass(values);

            objects.add(object);
        }

        return objects;
    }

    public static Object findById(String id){
        return find(new String[]{"id"},new String[]{id});
    }

    public static Object find(String selection, String selectionArg){
        return find(new String[]{selection},new String[]{selectionArg});
    }

    public static Object find(String[] selection, String[] selectionArgs){
        ArrayList<String> object = DBManager.find(table,columns,selection,selectionArgs);
        if(object.size()>0){
            return convertToClass(object);
        }else{
            return null;
        }
    }

    public static Privacy convertToClass(ArrayList<String> object){
        String id = object.get(0);
        String title = object.get(1);
        String content = object.get(2);

        Privacy privacy = new Privacy(id, title, content);

        return privacy;
    }

    public ContentValues generateValues(){
        ContentValues values = new ContentValues();

        values.put("id",id);
        values.put("title",title);
        values.put("content",content);

        return values;
    }
}
