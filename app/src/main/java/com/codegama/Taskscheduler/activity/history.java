package com.codegama.Taskscheduler.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.codegama.Taskscheduler.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;

public class history extends AppCompatActivity {

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        text=findViewById(R.id.month);

        BarChart barChart=findViewById(R.id.bar);
        String monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
        text.setText("statistics of "+monthname);
        ArrayList<BarEntry> visitors=new ArrayList<>();
        SharedPreferences sharedPreferences=getSharedPreferences("our_data", Context.MODE_PRIVATE);

        // get date-wise completed task count from shared preference
        //  and assign in bar-chart

        for(int i=1;i<=31;i++)
        {
            String key=Integer.toString(i);
            String result_string=sharedPreferences.getString(key,"0");
            Integer result_int=Integer.parseInt(result_string);
            visitors.add(new BarEntry(i,(int)result_int));
        }
        String[] array=new String[100];
        for(int i=0;i<=31;i++)
        {
            array[i]=Integer.toString(i);
        }
        XAxis xAxis=barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(array));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(false);
        xAxis.setGranularityEnabled(true);
        //hide
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        // reduce space
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisRight().setAxisMinimum(0);


        BarDataSet barDataSet=new BarDataSet(visitors,"Date-wise completed Task");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barDataSet.setDrawValues(false);


        BarData barData=new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.animateY(2000);
    }
}