package com.example.garrido.screensize;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class AdaptativeActivityFragment extends Fragment {

    public AdaptativeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                view = inflater.inflate(R.layout.fragment_adaptative_tablet, container, false);
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                view = inflater.inflate(R.layout.fragment_adaptative_tablet, container, false);
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                view = inflater.inflate(R.layout.fragment_adaptative_smartphone, container, false);
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                view = inflater.inflate(R.layout.fragment_adaptative_smartphone, container, false);
                break;
            case Configuration.SCREENLAYOUT_SIZE_UNDEFINED:
                view = inflater.inflate(R.layout.fragment_adaptative_smartphone, container, false);
                break;
            default:
                view = inflater.inflate(R.layout.fragment_adaptative_smartphone, container, false);
                break;
        }

        Button b1 = view.findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Boton 1 pulsado",Toast.LENGTH_LONG).show();
            }
        });

        Button b2 = view.findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Boton 2 pulsado",Toast.LENGTH_LONG).show();
            }
        });

        return view;

    }
}
