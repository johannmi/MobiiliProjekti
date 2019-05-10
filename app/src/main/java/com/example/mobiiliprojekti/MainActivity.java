package com.example.mobiiliprojekti;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Main activity - displays journal entries
 *
 * TEHNYT JOHANNA
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private List<Notes> notesList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesList = new ArrayList<>();  // List of Note objects
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);

        updateUI();
    }

    /**
     * updateUI() adds all data to the UI from the database
     */

    private void updateUI() {
        Cursor data = dbHelper.getData();   // Get data from database
        int imageID = R.drawable.moodimg3;  // Default mood image
        String moodName = "Default";        // Default mood

        // Clears the list when updateUI() is called
        notesList.clear();

        // Iterates through the data from database
        while(data.moveToNext()) {

            // Selects mood name and image depending on what mood is stored in the database
            if (data.getString(3).equals("5")) {
                moodName = "Erinomainen";
                imageID = R.drawable.moodimg5;
            } else if (data.getString(3).equals("4")) {
                moodName = "Hyvä";
                imageID = R.drawable.moodimg4;
            } else if (data.getString(3).equals("3")) {
                moodName = "Neutraali";
                imageID = R.drawable.moodimg3;
            } else if (data.getString(3).equals("2")) {
                moodName = "Huono";
                imageID = R.drawable.moodimg2;
            } else if (data.getString(3).equals("1")) {
                moodName = "Kamala";
                imageID = R.drawable.moodimg1;
            }

            // Adds Note object with data from database to the list
            notesList.add(
                new Notes(
                        Integer.parseInt(data.getString(0)),
                        imageID,
                        data.getString(1),
                        data.getString(2),
                        moodName,
                        data.getString(4),
                        data.getString(5)
                    ));
        }

        // Reverses list so the newest entry is first
        Collections.reverse(notesList);

        // Creates and assigns an adapter to the recycler view, notesList passed as a parameter
        adapter = new NotesAdapter(this, notesList);
        recyclerView.setAdapter(adapter);
    }

    // Button click switches to DoneToday activity
    public void addNote(View v) {
        Intent intent = new Intent(this, DoneToday.class);
        startActivity(intent);
    }

    // Adds functionality to the buttons at the bottom of the screen
    // NOTE!: The last one on the right does nothing, no time to add extra
    public void switchActivity(View v) {
        switch(v.getId())
        {
            case R.id.buttonBot3:
                Intent intent1 = new Intent(this, DoneToday.class);
                startActivity(intent1);
                break;
            case R.id.buttonBot2:
                Intent intent2 = new Intent(this, StatisticsActivity.class);
                startActivity(intent2);
                break;
            case R.id.buttonBot1:

                break;
        }
    }

}
