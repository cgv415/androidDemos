package com.example.endesa_app.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
//import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AppManager {
    public static Context context;
    public static boolean showList;
    public AppManager(Context context) {
        this.context = context;
    }

    public static void start(Context context){
        context = context;

        DBManager.instance(context);
        DBManager.checkDb();
        showList = false;
    }
    /*public static boolean gestionarNavigationDrawer(NavigationView nv, DrawerLayout drawer, MenuItem item, Context context) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        Menu m=nv.getMenu();
        if (id == R.id.nav_projects) {

            showList=!m.findItem(R.id.nav_current).isVisible();
            setExpandibleList(m);
            return true;

        } else if (id == R.id.nav_current) {
            intent = new Intent(context,StepsActivity.class);
            User.setRangeProjects("current");
        } else if (id == R.id.nav_old) {
            intent = new Intent(context,AboutActivity.class);
            User.setRangeProjects("old");
        } else if (id == R.id.nav_future) {
            intent = new Intent(context,HomeActivity.class);
            User.setRangeProjects("future");
        } else if (id == R.id.nav_all) {
            intent = new Intent(context,HomeActivity.class);
            User.setRangeProjects("all");
        } else if(id == R.id.nav_home){
            intent = new Intent(context,HomeActivity.class);
        }else if(id == R.id.nav_download){
            sendDB("answers");
        }*//* else if (id == R.id.nav_share) {
            Toast.makeText(context,"Estamos trabajando en ello",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(context,"Estamos trabajando en ello",Toast.LENGTH_LONG).show();
        }*//*
        if(intent != null){
            User.update();
            context.startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public static void popUpAlert(final String type){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String title = type.equals("download")?"Confirmar descarga de datos":"Confirmar envío de datos";
        builder.setTitle(title);
        builder.setMessage(R.string.sinc_alert);
        builder.setPositiveButton(R.string.go, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                boolean sinc = false;
                switch (type){
                    case "download":
                        sinc = SyncManager.download(context);
                        break;
                    case "upload":
                        sinc = SyncManager.upload(context);
                        break;
                }
                if(!sinc){
                    Toast.makeText(context,"Error de sincronización",Toast.LENGTH_LONG).show();
                }
            }
        })
                .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
    }

    public static void setExpandibleList(Menu m){

        MenuItem projects = m.findItem(R.id.nav_projects);
        if(showList){
            projects.setIcon(R.mipmap.ic_hide_list);
        }else{
            projects.setIcon(R.mipmap.ic_show_list);
        }
        m.findItem(R.id.nav_current).setVisible(showList);
        m.findItem(R.id.nav_old).setVisible(showList);
        m.findItem(R.id.nav_future).setVisible(showList);
        m.findItem(R.id.nav_all).setVisible(showList);
    }

    public static void setTitleNavigationDrawer(NavigationView navigationView){
        View headerView = navigationView.getHeaderView(0);
        TextView tvUno = headerView.findViewById(R.id.tv_headerUno);
        tvUno.setText(User.getCompleteName());

        TextView tvDos = headerView.findViewById(R.id.tv_headerDos);
        tvDos.setText(User.getEmail());
    }

    public static void gestionarMenu(MenuItem item, Context context) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        Intent intent;

        switch (id){
            case R.id.settings:
                //context.startActivity(new Intent(context,ProfileActivity.class));
                Toast.makeText(context,"Estamos trabajando en ello",Toast.LENGTH_LONG).show();
                break;
            case R.id.logout:
                User.logout();
                context.startActivity(new Intent(context,MainActivity.class));
                break;
        }
    }

    *//**
     * Formato usuario
     * @return
     *//*
    public static String getFormatDateTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return mdformat.format(calendar.getTime());
        //10/03/2019 16:12
    }

    *//**
     * Formato servidor
     * @return
     *//*
    public static String getDateTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return mdformat.format(calendar.getTime());
        //2019-03-13 16:12:56
    }

    public static String parseDate(String time, String format) {
        String inputPattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(format);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static <T> ArrayList<T> cast(ArrayList list) {
        return list;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public static int getPX(int dp){
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return (int) Math.ceil(dp * logicalDensity);
    }

    public static void sendDB(String tableName){
        DBHelper dbhelper = new DBHelper(context);
        File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, tableName + ".csv");
        try
        {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM " + tableName,null);
            csvWrite.writeNext(curCSV.getColumnNames());

            while(curCSV.moveToNext())
            {
                //Which column you want to exprort
                String arrStr[] ={
                        curCSV.getString(0),
                        curCSV.getString(1),
                        curCSV.getString(2),
                        curCSV.getString(3),
                        curCSV.getString(4),
                        curCSV.getString(5),
                        curCSV.getString(6),
                        curCSV.getString(7),
                };

                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();

            String email = "cgarrido@suratica.es";
            String subject = "Base de datos";
            String message = "Datos de la tabla answers enviados desde la aplicación SVC por " + User.getCompleteName();
            String filePath = file.getAbsolutePath();
            //Uri URI = Uri.fromFile(new File(path));
            String authority = context.getApplicationContext().getPackageName() + "fileprovider";

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            File filelocation = new File(filePath);
            Uri path = Uri.fromFile(filelocation);
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("vnd.android.cursor.dir/email");
            String to[] = {email};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
            emailIntent.putExtra(Intent.EXTRA_STREAM, path);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, message);
            context.startActivity(Intent.createChooser(emailIntent , "Send email..."));

        }catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
    }*/

    public static String getFormatDateTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return mdformat.format(calendar.getTime());
        //10/03/2019 16:12
    }

    public static String getDateTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return mdformat.format(calendar.getTime());
        //2019-03-13 16:12:56
    }

    public static String parseDate(String time, String format) {
        String inputPattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(format);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static <T> ArrayList<T> cast(ArrayList list) {
        return list;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public static int getPX(int dp){
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return (int) Math.ceil(dp * logicalDensity);
    }
}
