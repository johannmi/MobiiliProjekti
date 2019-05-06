package com.example.mobiiliprojekti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Creates and manages the database
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Table and column names
    private static final String TAG = "Database_Helper";
    private static final String TABLE_NAME = "notes_table";
    private static final String COL0 = "id";
    private static final String COL1 = "date";
    private static final String COL2 = "time";
    private static final String COL3 = "mood";
    private static final String COL4 = "done";
    private static final String COL5 = "notes";

    /**
     * Creates an instance of the database helper
     * @param context
     */

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    /**
     * Creates table notes_table
     * @param db
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 + " TEXT," +
                COL2 + " TEXT," +
                COL3 + " TEXT," +
                COL4 + " TEXT," +
                COL5 + " TEXT)";
        db.execSQL(createTable);
    }

    /**
     * For upgrading the table
     * @param db
     * @param oldVersion
     * @param newVersion
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Adds data to database
     * @param date Date of the entry
     * @param time Time of entry
     * @param mood User's mood
     * @param done What the user did today
     * @param notes User's own notes
     * @return Returns a boolean value. True if data was added correctly
     */

    public boolean addData(String date, String time, String mood, String done, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, date);
        contentValues.put(COL2, time);
        contentValues.put(COL3, mood);
        contentValues.put(COL4, done);
        contentValues.put(COL5, notes);

        Log.d(TAG, "Adding to database");
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Selects whole table and returs the data
     * @return Returns the data in the table
     */

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Deletes all data from the table
     * Used for testing purposes
     */

    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}














