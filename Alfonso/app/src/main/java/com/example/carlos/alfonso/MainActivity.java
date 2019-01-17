package com.example.carlos.alfonso;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tv_dulce_salado;
    private TextView tv_dulce;
    private TextView tv_salado;

    private RadioButton hambreSi;
    private RadioButton hambreNo;

    private RadioButton dulce;
    private RadioButton salado;

    private RadioButton bollo;
    private RadioButton galleta;

    private RadioButton pizza;
    private RadioButton hamburguesa;


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

        hambreSi =(RadioButton) findViewById(R.id.rB_hambreSi);
        hambreNo =(RadioButton) findViewById(R.id.rB_hambreNo);
        dulce =(RadioButton) findViewById(R.id.rB_dulce);
        salado =(RadioButton) findViewById(R.id.rB_salado);
        bollo =(RadioButton) findViewById(R.id.rB_bollo);
        galleta =(RadioButton) findViewById(R.id.rB_galleta);
        pizza =(RadioButton) findViewById(R.id.rB_pizza);
        hamburguesa =(RadioButton) findViewById(R.id.rB_hamburguesa);

        tv_dulce_salado = (TextView) findViewById(R.id.tv_dulce_salado);
        tv_dulce = (TextView) findViewById(R.id.tv_dulce);
        tv_salado = (TextView) findViewById(R.id.tv_salado);

        dulce.setVisibility(View.INVISIBLE);
        salado.setVisibility(View.INVISIBLE);
        bollo.setVisibility(View.INVISIBLE);
        galleta.setVisibility(View.INVISIBLE);
        pizza.setVisibility(View.INVISIBLE);
        hamburguesa.setVisibility(View.INVISIBLE);
        tv_dulce_salado.setVisibility(View.INVISIBLE);
        tv_dulce.setVisibility(View.INVISIBLE);
        tv_salado.setVisibility(View.INVISIBLE);

        hambreSi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hambreNo.setChecked(false);
                    tv_dulce_salado.setVisibility(View.VISIBLE);
                    dulce.setVisibility(View.VISIBLE);
                    salado.setVisibility(View.VISIBLE);
                }
            }
        });

        hambreNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hambreSi.setChecked(false);

                    dulce.setVisibility(View.INVISIBLE);
                    salado.setVisibility(View.INVISIBLE);
                    bollo.setVisibility(View.INVISIBLE);
                    galleta.setVisibility(View.INVISIBLE);
                    pizza.setVisibility(View.INVISIBLE);
                    hamburguesa.setVisibility(View.INVISIBLE);
                    tv_dulce_salado.setVisibility(View.INVISIBLE);
                    tv_dulce.setVisibility(View.INVISIBLE);
                    tv_salado.setVisibility(View.INVISIBLE);
                }
            }
        });

        dulce.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hambreNo.setChecked(false);
                    tv_dulce_salado.setVisibility(View.VISIBLE);
                    dulce.setVisibility(View.VISIBLE);
                    salado.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
