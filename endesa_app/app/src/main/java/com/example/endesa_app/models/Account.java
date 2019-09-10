package com.example.endesa_app.models;

import android.content.ContentValues;

import com.example.endesa_app.utils.DBManager;

import org.json.JSONObject;

import java.util.ArrayList;

public class Account {
    public static String table = "account";
    private static String[] columns = new String[]{"id","alias", "password","avatar","postal_code","access_token","active"};
    public static final String createTable =
            "create table " + table + " (" +
                    "id" + " integer PRIMARY KEY AUTOINCREMENT," +
                    "alias text," +
                    "password text," +
                    "avatar text," +
                    "postal_code text," +
                    "access_token text," +
                    "active boolean," +
                    "created_at text," +
                    "updated_at text" +
                    ");";

    private String id;
    private String alias;
    private String password;
    private String avatar;
    private String postal_code;
    private String access_token;
    private boolean active;

    public Account(String id, String alias, String password, String avatar, String postal_code, String access_token, boolean active) {
        this.id = id;
        this.alias = alias;
        this.password = password;
        this.avatar = avatar;
        this.postal_code = postal_code;
        this.access_token = access_token;
        this.active = active;
    }

    public Account(JSONObject account){
        try{
            if(account.has("id")){
                this.id = account.getString("id");
            }

            if(account.has("alias")){
                this.alias = account.getString("alias");
            }

            if(account.has("password")){
                this.password = account.getString("password");
            }

            if(account.has("avatar")){
                this.avatar = account.getString("avatar");
            }

            if(account.has("postal_code")){
                this.postal_code = account.getString("postal_code");
            }

            if(account.has("access_token")){
                this.access_token = account.getString("access_token");
            }

            if(account.has("active")){
                this.active = account.getBoolean("active");
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Account { " +
                "id : '" + id + '\'' +
                ", alias : '" + alias + '\'' +
                ", password : '" + password + '\'' +
                ", avatar : '" + avatar + '\'' +
                ", postal_code : '" + postal_code + '\'' +
                ", access_token : '" + access_token + '\'' +
                ", active : '" + active + '\'' +
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
        if(getId() != null && DBManager.exists(table,getId())){
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

    public static Account convertToClass(ArrayList<String> object){
        String id = object.get(0);
        String alias = object.get(1);
        String password = object.get(2);
        String avatar = object.get(3);
        String postal_code = object.get(4);
        String access_token = object.get(5);
        String strActive = object.get(6);
        boolean active = strActive.equals("1");
        //boolean active = strActive == "1";

        Account account = new Account(id, alias, password, avatar, postal_code, access_token, active);

        return account;
    }

    public ContentValues generateValues(){
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("alias",alias);
        values.put("password",password);
        values.put("avatar",avatar);
        values.put("postal_code",postal_code);
        values.put("access_token",access_token);
        values.put("active",active);

        return values;
    }
}
