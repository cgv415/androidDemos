package com.example.carlos.navigationdrawer;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TabHost;

import java.util.ArrayList;

/**
 * Created by Carlos on 31/01/2016.
 */
public class Empleado extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DataBaseManager manager;
    private Button actualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        manager = new DataBaseManager(this);
        setContentView(R.layout.empleado);

        TabHost tab = (TabHost) findViewById(R.id.tabHost);

        tab.setup();

        TabHost.TabSpec spec;
        ArrayList<String> espacios = manager.obtenerEspacios();
        for(int i = 0; i<espacios.size(); i++){
            spec = tab.newTabSpec("TAB");
            //Log.d("Espacio",espacios.get(i));//Remove
            switch (i){
                case 0:
                    this.cargarmesas(espacios.get(i));
                    spec.setContent(R.id.tab0);
                    break;
                case 1:
                    spec.setContent(R.id.tab1);
                    break;
                case 2:
                    spec.setContent(R.id.tab2);
                    break;
                case 3:
                    spec.setContent(R.id.tab3);
                    break;
                default:
                    break;
            }
            spec.setIndicator(espacios.get(i));
            tab.addTab(spec);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empleado, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_actualizar:
                actualizar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void actualizar(){
        setContentView(R.layout.content_actualizar);
        actualizar = (Button) findViewById(R.id.btn_actualizar);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox ck = (CheckBox) findViewById(R.id.todo);
                if(ck.isChecked()){
                    manager.actualizar();
                }
                setContentView(R.layout.empleado);
            }
        });

    }

    /*extends AppCompatActivity implements View.OnClickListener*/

    public void cargarmesas(String espacio){
        ArrayList<ArrayList<String>> mesas = manager.buscarMesaPorEspacio(espacio);
        GridLayout ll = (GridLayout)findViewById(R.id.tab0);
        ImageButton btn;
        int id;
        for(int i = 0 ; i < mesas.size();i++){
            btn = new ImageButton(this);
            if(mesas.get(i).get(3).equals("0")){
                //Log.d("Estado", "Libre");//Remove
                btn.setImageResource(R.mipmap.ic_mesa_green);
            }else{
                //Log.d("Estado", "Reservado");
                btn.setImageResource(R.mipmap.ic_mesa_black);
            }
            id = Integer.parseInt(mesas.get(i).get(0));
            btn.setId(id);
            ll.addView(btn);

            //Log.d("Mesa"+mesas.get(i).get(0),""+mesas.get(i));//Remove
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
