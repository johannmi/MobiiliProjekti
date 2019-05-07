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

/*
* MainActivity
*
*
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

        notesList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);

        updateUI();
    }

    private void updateUI() {
        Cursor data = dbHelper.getData();
        int imageID = R.drawable.moodimg3;
        String moodName = "Default";

        notesList.clear();
        //dbHelper.deleteData();

        while(data.moveToNext()) {

            // Selects mood name and image
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

        Collections.reverse(notesList);
        adapter = new NotesAdapter(this, notesList);
        recyclerView.setAdapter(adapter);
    }

    public void addNote(View v) {
        Intent intent = new Intent(this, DoneToday.class);
        startActivity(intent);
    }

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
