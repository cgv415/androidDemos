package com.example.carlos.simplebargraph;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

//https://www.youtube.com/watch?v=pi1tq-bp7uA
//https://www.youtube.com/watch?v=ll281FcXtjQ

public class MainActivity extends AppCompatActivity {

    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barChart = (BarChart) findViewById(R.id.bargraph);

        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("Enero");
        theDates.add("Febrero");
        theDates.add("Marzo");
        theDates.add("Abril");
        theDates.add("Mayo");
        theDates.add("Junio");

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(5f,0));
        barEntries.add(new BarEntry(10f,1));
        barEntries.add(new BarEntry(15f,2));
        barEntries.add(new BarEntry(20f,3));
        barEntries.add(new BarEntry(25f,4));
        barEntries.add(new BarEntry(30f,5));
        BarDataSet barDataEntries = new BarDataSet(barEntries,"Dates");

        barDataEntries.setBarBorderColor(Color.RED);


        BarData theData = new BarData(barDataEntries);
        barChart.setData(theData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisMaximum(6);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(theDates));
        //barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(theDates));
    }
}
