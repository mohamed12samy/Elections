package com.example.elections.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.elections.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bie_chart);
        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> visitors = new ArrayList<>();
        visitors.add(new PieEntry(522,"محمد سامي"));
        visitors.add(new PieEntry(355,"محمد رضا"));
        visitors.add(new PieEntry(156,"محمد عبدالله"));
        visitors.add(new PieEntry(86,"4"));
        visitors.add(new PieEntry(45,"5"));
        visitors.add(new PieEntry(45,"6"));
        visitors.add(new PieEntry(45,"7"));
        visitors.add(new PieEntry(45,"8"));
        visitors.add(new PieEntry(45,"9"));
        PieDataSet pieDataSet = new PieDataSet(visitors,"المرشحين");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("المرشحين ل سنه 2020");
        pieChart.animate();

    }
}