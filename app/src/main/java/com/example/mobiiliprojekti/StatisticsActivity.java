package com.example.mobiiliprojekti;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;

public class StatisticsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private GraphView graph;
    private DatabaseHelper dbHelper;
    private TextView textAverage, textViewGraph;
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
        textAverage = findViewById(R.id.textAverage);
        dbHelper = new DatabaseHelper(this);
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        textViewGraph = findViewById(R.id.textViewGraph);

        //graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);

        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(7);
        graph.getViewport().setMinY(1);
        graph.getViewport().setMaxY(5);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

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
            textAverage.setTextColor(getResources().getColor(R.color.mood5));
        } else if (averageMood >= 3.5) {
            textAverage.setTextColor(getResources().getColor(R.color.mood4));
        } else if (averageMood >= 2.5) {
            textAverage.setTextColor(getResources().getColor(R.color.mood3));
        } else if (averageMood >= 1.5) {
            textAverage.setTextColor(getResources().getColor(R.color.mood2));
        } else {
            textAverage.setTextColor(getResources().getColor(R.color.mood1));
        }

        textAverage.setText(df.format(averageMood));
        textViewGraph.setText(amount + " uusinta merkintää");

        series.setDrawDataPoints(true);
        graph.addSeries(series);

    }


}
