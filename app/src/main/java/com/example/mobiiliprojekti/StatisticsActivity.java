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

/**
 * StatisticsActivity implements AdapterView.OnItemSelectedListener for a spinner
 *
 * TEHNYT JOHANNA
 */

public class StatisticsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private GraphView graph, barGraph;
    private DatabaseHelper dbHelper;
    private TextView textAverage, textViewGraph, textGraphAverage;
    private double averageMood;
    private static DecimalFormat df = new DecimalFormat("0.0"); // For rounding doubles
    private int amount = 7; // Default value for entries displayed
    private Cursor data;
    private Spinner spinner;

    /**
     * Finds views and sets graph settings
     * @param savedInstanceState Bundle
     */

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

        // Settings for line graph

        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(7);
        graph.getViewport().setMinY(1);
        graph.getViewport().setMaxY(5);

        // Settings for bar graph

        barGraph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        barGraph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        barGraph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);

        barGraph.getViewport().setYAxisBoundsManual(true);
        barGraph.getViewport().setXAxisBoundsManual(true);

        barGraph.getViewport().setMinX(0.5);
        barGraph.getViewport().setMaxX(7.5);
        barGraph.getViewport().setMinY(0);
        barGraph.getViewport().setMaxY(6);

        // Creates an adapter for spinner and sets it
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        updateUI();
    }

    /**
     * Checks which item in the spinner is selected and
     * changes the value of amount accordingly
     * @param parent AdapterView for spinner
     * @param view View
     * @param pos position of item selected
     * @param id long
     */

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        amount = Integer.parseInt(parent.getItemAtPosition(pos).toString());
        updateUI();
    }

    /**
     * Default value for amount
     * @param parent AdapterView
     */

    public void onNothingSelected(AdapterView<?> parent) {
        amount = 7;
        updateUI();
    }

    /**
     * Updates the line graph and calculates average mood
     */

    private void updateUI() {
        data = dbHelper.getLatest(amount);      // Get specified amount of rows from the bottom of the database table
        graph.getViewport().setMaxX(amount);    // Set graph bounds
        int dataY, position = 1;                // Values to be added to graph
        DataPoint datapoint;
        averageMood = 0;

        // Resets graph series
        graph.removeAllSeries();
        // Creates new graph series
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {});
        // Graph styling
        series.setColor(getResources().getColor(R.color.colorGraph));
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(60,248, 122, 255));

        // Iterates through data from database
        while(data.moveToNext()) {
            dataY = Integer.parseInt(data.getString(3));             // Get mood value
            datapoint = new DataPoint(position,dataY);                          // Make datapoint
            series.appendData(datapoint, false, amount, false); // Add datapoint to graph
            position++;                                                         // Increase position (X axis value)
            averageMood += dataY;
        }

        // Calculates average mood
        if (amount <= dbHelper.getSize()) {
            averageMood = averageMood / amount;
        } else {
            averageMood = averageMood / dbHelper.getSize();
        }

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

        // Draw data points
        series.setDrawDataPoints(true);
        graph.addSeries(series);

        // Call update to the bar graph
        updateDailyAverage();
    }

    /**
     * Updates the bar graph
     */

    public void updateDailyAverage() {
        data = dbHelper.getDistinct();                  // Get all distinct dates from database
        double dailyAverage;
        int averageDivider;
        ArrayList<String> dateList = new ArrayList<>(); // List of distinct dates
        barGraph.getViewport().setMaxX(amount);         // Set graph bounds

        // Reset and create new series for bar graph
        barGraph.removeAllSeries();
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {});

        // Add distinct dates to dateList
        while(data.moveToNext()) {
            dateList.add(data.getString(0));
        }

        // If the number of dates is larger that the amount of displayed dates
        // create a sublist that only includes the last dates so that
        // the list size is always the same (or smaller) as variable "amount"
        if (dateList.size() > amount) {
            dateList = new ArrayList<>(dateList.subList(dateList.size()-amount, dateList.size()));
        }

        // Iterates through dateList to calculate mood average for each day
        for(int position=0; position<dateList.size(); position++) {
            data = dbHelper.getInfoFromDate(dateList.get(position));    // Get data from date
            averageDivider = 0;
            dailyAverage = 0;

            // Go through data and add it to dailyAverage
            while (data.moveToNext()) {
                dailyAverage += Integer.parseInt(data.getString(2));
                averageDivider++;
            }

            // Calculate dailyAverage
            dailyAverage = dailyAverage / averageDivider;

            // Round average
            double roundedAverage = Math.round(dailyAverage * 10) / 10.0;

            // Add average data to the bar graph
            series.appendData(new DataPoint((position+1), roundedAverage), false, amount, false);
        }

        // Set color for bars depending on mood (Y axis value)
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

        // Display Y axis value numbers on top of bars if less than 30 days are shown
        if (amount == 30) {
            series.setDrawValuesOnTop(false);
        } else {
            series.setDrawValuesOnTop(true);
        }

        textGraphAverage.setText(amount + " päivän keskimääräinen mieliala");

        // Style and draw bar graph
        series.setValuesOnTopColor(Color.GRAY);
        series.setSpacing(20);
        barGraph.addSeries(series);
    }

}
