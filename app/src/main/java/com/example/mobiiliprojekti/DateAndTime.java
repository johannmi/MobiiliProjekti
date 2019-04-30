package com.example.mobiiliprojekti;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateAndTime {
    private Calendar cal;
    private Date date;
    private DateFormat dateFormat;
    private String formattedDate;
    Map<Integer, String> dayNames = new HashMap<>();

    public DateAndTime() {
        dayNames.put(1, "Sunnuntai");
        dayNames.put(2, "Maanantai");
        dayNames.put(3, "Tiistai");
        dayNames.put(4, "Keskiviikko");
        dayNames.put(5, "Torstai");
        dayNames.put(6, "Perjantai");
        dayNames.put(7, "Lauantai");
    }

    public String getFullDate() {
        cal = Calendar.getInstance();
        date = cal.getTime();
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public String getDay() {
        cal = Calendar.getInstance();
        date = cal.getTime();
        dateFormat = new SimpleDateFormat("dd");
        formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public String getTime() {
        cal = Calendar.getInstance();
        date = cal.getTime();
        dateFormat = new SimpleDateFormat("HH:mm");
        formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public String getDayName() {
        date = new Date();
        cal = Calendar.getInstance();
        cal.setTime(date);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        return dayNames.get(dayNum);
    }
}
