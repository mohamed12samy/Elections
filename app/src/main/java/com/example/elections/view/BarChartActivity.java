package com.example.elections.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.elections.R;
import com.example.elections.model.Candidates;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        com.github.mikephil.charting.charts.BarChart barChart = findViewById(R.id.barChart);


     /*   Bundle bundle = getIntent().getExtras();
        candidates =  bundle.getParcelable("data");
*/

        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(2010,12));
        visitors.add(new BarEntry(2013,15));
        visitors.add(new BarEntry(2015,18));
        visitors.add(new BarEntry(2017,184));
        visitors.add(new BarEntry(2020,1531));
        visitors.add(new BarEntry(2012,11));

        BarDataSet barDataSet = new BarDataSet(visitors,"المرشحين");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("رسم بياني للمرشحين");
        barChart.animateY(2000);
    }
}