package com.example.mobiiliprojekti;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private DateAndTime dateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);
        dateAndTime = new DateAndTime();

        updateUI();
    }

    private void updateUI() {
        Cursor data = dbHelper.getData();
        int imageID = R.drawable.smile;
        String moodName = "Default";

        notesList.clear();
        //dbHelper.deleteData();

        while(data.moveToNext()) {

            if (data.getString(3).equals("1")) {
                moodName = "Erinomainen";
                imageID = R.drawable.smile;
            } else if (data.getString(3).equals("2")) {
                moodName = "Hyvä";
                imageID = R.drawable.smile;
            } else if (data.getString(3).equals("3")) {
                moodName = "Neutraali";
                imageID = R.drawable.smile;
            } else if (data.getString(3).equals("4")) {
                moodName = "Huono";
                imageID = R.drawable.smile;
            } else if (data.getString(3).equals("5")) {
                moodName = "Hyvin huono";
                imageID = R.drawable.smile;
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
        boolean insertData = dbHelper.addData(
                dateAndTime.getFullDate(),
                dateAndTime.getDayName() + " kello " + dateAndTime.getTime(),
                "1",
                "opiskelu • kaverit • urheilu • rentoutuminen",
                "Tänään oli hyvä päivä :)"
        );

        if (insertData) {
            toastMessage("Merkintä lisätty");
        } else {
            toastMessage("Jotain meni pieleen");
        }

        updateUI();
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void switchActivity(View v) {
        switch(v.getId())
        {
            case R.id.buttonBot1:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.buttonBot2:
                Intent intent2 = new Intent(this, StatisticsActivity.class);
                startActivity(intent2);
                break;
            case R.id.buttonBot3:

                break;
        }
    }

}
