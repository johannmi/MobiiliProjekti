package com.example.mobiiliprojekti;

/**
 * Contains all information needed for one journal entry
 * Information is stored in a database, put into an instance of Notes
 * and Notes added to a list that is used tp create the main UI and shows the information
 */

public class Notes {
    private int id, image;
    private String date, time, mood, done, notes;

    /**
     *
     * @param id ID from database
     * @param image Image id
     * @param date Date of the journal entry
     * @param time Time of the entry
     * @param mood User's mood
     * @param done What the user did today
     * @param notes User's own notes
     */

    public Notes(int id, int image, String date, String time, String mood, String done, String notes) {
        this.id = id;
        this.image = image;
        this.date = date;
        this.time = time;
        this.mood = mood;
        this.done = done;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    /**
     *
     * @return Returns image id
     */

    public int getImage() {
        return image;
    }

    /**
     *
     * @return Returns date
     */

    public String getDate() {
        return date;
    }

    /**
     *
     * @return Returns time
     */

    public String getTime() {
        return time;
    }

    /**
     *
     * @return Returns mood
     */

    public String getMood() {
        return mood;
    }

    /**
     *
     * @return Returns what the user did today
     */

    public String getDone() {
        return done;
    }

    /**
     *
     * @return Returns the user's own notes
     */

    public String getNotes() {
        return notes;
    }
}
