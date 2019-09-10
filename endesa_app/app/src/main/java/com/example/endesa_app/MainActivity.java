package com.example.endesa_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.endesa_app.models.Account;
import com.example.endesa_app.utils.AppManager;
import com.example.endesa_app.utils.DBManager;
import com.example.endesa_app.utils.SyncManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppManager.start(this);
        //AppManager.context = this;
        //manager = new DBManager(this);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SyncManager.signup(getApplicationContext(),"CGV2","suratica");
                //SyncManager.login(getApplicationContext(),"CGV2","suratica");

                SyncManager.getAbout(getApplicationContext());
                SyncManager.getPrivacy(getApplicationContext());

                Log.d("Main","accounts");
            }
        });
    }
}
