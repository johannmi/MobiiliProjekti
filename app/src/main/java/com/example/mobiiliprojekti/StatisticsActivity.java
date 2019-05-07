package com.example.mobiiliprojekti;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class StatisticsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private GraphView graph, barGraph;
    private DatabaseHelper dbHelper;
    private TextView textAverage, textViewGraph, textGraphAverage;
    private double averageMood;
    private static DecimalFormat df = new DecimalFormat("0.0");
    private int amount = 7;
    private Cursor data;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        graph = findViewById(R.id.graph);
        barGraph = findViewById(R.id.graphAverage);
        textAverage = findViewById(R.id.textAverage);
        textGraphAverage = findViewById(R.id.textViewGraphAverage);
        dbHelper = new DatabaseHelper(this);
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        textViewGraph = findViewById(R.id.textViewGraph);

        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(7);
        graph.getViewport().setMinY(1);
        graph.getViewport().setMaxY(5);

        barGraph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        barGraph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        barGraph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);

        barGraph.getViewport().setYAxisBoundsManual(true);
        barGraph.getViewport().setXAxisBoundsManual(true);

        barGraph.getViewport().setMinX(0.5);
        barGraph.getViewport().setMaxX(7.5);
        barGraph.getViewport().setMinY(0);
        barGraph.getViewport().setMaxY(6);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        updateUI();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        amount = Integer.parseInt(parent.getItemAtPosition(pos).toString());
        updateUI();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        amount = 7;
        updateUI();
    }

    private void updateUI() {
        data = dbHelper.getLatest(amount);
        graph.getViewport().setMaxX(amount);
        int dataY, position = 1;
        DataPoint datapoint;
        averageMood = 0;

        graph.removeAllSeries();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {});
        series.setColor(getResources().getColor(R.color.colorGraph));
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(60,248, 122, 255));

        while(data.moveToNext()) {
            dataY = Integer.parseInt(data.getString(3));
            datapoint = new DataPoint(position,dataY);
            series.appendData(datapoint, false, amount, false);
            position++;
            averageMood += dataY;
        }

        // Calculates average mood
        averageMood = averageMood/amount;

        // Sets color for average mood
        if (averageMood >= 4.5) {
            textAverage.setBackgroundColor(getResources().getColor(R.color.mood5));
        } else if (averageMood >= 3.5) {
            textAverage.setBackgroundColor(getResources().getColor(R.color.mood4));
        } else if (averageMood >= 2.5) {
            textAverage.setBackgroundColor(getResources().getColor(R.color.mood3));
        } else if (averageMood >= 1.5) {
            textAverage.setBackgroundColor(getResources().getColor(R.color.mood2));
        } else {
            textAverage.setBackgroundColor(getResources().getColor(R.color.mood1));
        }

        textAverage.setText(df.format(averageMood));
        textViewGraph.setText(amount + " uusinta merkintää");

        series.setDrawDataPoints(true);
        graph.addSeries(series);

        updateDailyAverage();
    }

    public void updateDailyAverage() {
        data = dbHelper.getDistinct();
        double dailyAverage;
        int averageDivider;
        ArrayList<String> dateList = new ArrayList<>();
        barGraph.getViewport().setMaxX(amount);

        barGraph.removeAllSeries();
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {});

        while(data.moveToNext()) {
            dateList.add(data.getString(0));
        }

        if (dateList.size() > amount) {
            dateList = new ArrayList<>(dateList.subList(dateList.size()-amount, dateList.size()));
        }

        for(int position=0; position<dateList.size(); position++) {
            data = dbHelper.getInfoFromDate(dateList.get(position));
            averageDivider = 0;
            dailyAverage = 0;

            while (data.moveToNext()) {
                dailyAverage += Integer.parseInt(data.getString(2));
                averageDivider++;
            }

            dailyAverage = dailyAverage/averageDivider;

            series.appendData(new DataPoint((position+1), Double.valueOf(df.format(dailyAverage))), false, amount, false);
        }

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if (data.getY() >= 4.5) {
                    return getResources().getColor(R.color.mood5);
                } else if (data.getY() >= 3.5) {
                    return getResources().getColor(R.color.mood4);
                } else if (data.getY() >= 2.5) {
                    return getResources().getColor(R.color.mood3);
                } else if (data.getY() >= 1.5) {
                    return getResources().getColor(R.color.mood2);
                } else {
                    return getResources().getColor(R.color.mood1);
                }
            }
        });

        if (amount == 30) {
            series.setDrawValuesOnTop(false);
        } else {
            series.setDrawValuesOnTop(true);
        }

        textGraphAverage.setText(amount + " päivän keskimääräinen mieliala");

        series.setValuesOnTopColor(Color.GRAY);
        series.setSpacing(20);
        barGraph.addSeries(series);
    }

}
