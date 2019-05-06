package com.example.mobiiliprojekti;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Makes getting date and time easier
 * Methods return date or time in the format needed
 */

public class DateAndTime {
    private Calendar cal;
    private Date date;
    private DateFormat dateFormat, yearFormat, dayFormat;
    private String formattedDate, formattedYear, formattedDay, fullDate;
    Map<Integer, String> dayNames = new HashMap<>();    // Contains day names
    Map<String, String> monthNames = new HashMap<>();   // Contains month names

    public DateAndTime() {
        dayNames.put(1, "Sunnuntai");
        dayNames.put(2, "Maanantai");
        dayNames.put(3, "Tiistai");
        dayNames.put(4, "Keskiviikko");
        dayNames.put(5, "Torstai");
        dayNames.put(6, "Perjantai");
        dayNames.put(7, "Lauantai");

        monthNames.put("1", "tammikuuta");
        monthNames.put("2", "helmikuuta");
        monthNames.put("3", "maaliskuuta");
        monthNames.put("4", "huhtikuuta");
        monthNames.put("5", "toukokuuta");
        monthNames.put("6", "kesäkuuta");
        monthNames.put("7", "heinäkuuta");
        monthNames.put("8", "elokuuta");
        monthNames.put("9", "syyskuuta");
        monthNames.put("10", "lokakuuta");
        monthNames.put("11", "marraskuuta");
        monthNames.put("12", "joulukuuta");
    }

    /**
     *
     * @return  Returns full date in dd. monthname yyyy format
     */

    public String getFullDate() {
        cal = Calendar.getInstance();
        date = cal.getTime();

        // Get month
        dateFormat = new SimpleDateFormat("MM");
        formattedDate = dateFormat.format(date);

        // Get day
        dayFormat = new SimpleDateFormat("dd");
        formattedDay = dayFormat.format(date);

        // Get year
        yearFormat = new SimpleDateFormat("yyyy");
        formattedYear = yearFormat.format(date);

        // Removes the 0 in front of the month number if needed
        if (formattedDate.equals("01")) { formattedDate = "1"; }
        else if (formattedDate.equals("02")) { formattedDate = "2"; }
        else if (formattedDate.equals("03")) { formattedDate = "3"; }
        else if (formattedDate.equals("04")) { formattedDate = "4"; }
        else if (formattedDate.equals("05")) { formattedDate = "5"; }
        else if (formattedDate.equals("06")) { formattedDate = "6"; }
        else if (formattedDate.equals("07")) { formattedDate = "7"; }
        else if (formattedDate.equals("08")) { formattedDate = "8"; }
        else if (formattedDate.equals("09")) { formattedDate = "9"; }

        // Removes the 0 in front of the day number if needed
        if (formattedDay.equals("01")) { formattedDay = "1"; }
        else if (formattedDay.equals("02")) { formattedDay = "2"; }
        else if (formattedDay.equals("03")) { formattedDay = "3"; }
        else if (formattedDay.equals("04")) { formattedDay = "4"; }
        else if (formattedDay.equals("05")) { formattedDay = "5"; }
        else if (formattedDay.equals("06")) { formattedDay = "6"; }
        else if (formattedDay.equals("07")) { formattedDay = "7"; }
        else if (formattedDay.equals("08")) { formattedDay = "8"; }
        else if (formattedDay.equals("09")) { formattedDay = "9"; }

        fullDate = formattedDay + ". " + monthNames.get(formattedDate) + " " + formattedYear;

        return fullDate;
    }

    /**
     *
     * @return Returns day number
     */

    public String getDay() {
        cal = Calendar.getInstance();
        date = cal.getTime();
        dateFormat = new SimpleDateFormat("dd");
        formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    /**
     *
     * @return Returns time in HH:mm format
     */

    public String getTime() {
        cal = Calendar.getInstance();
        date = cal.getTime();
        dateFormat = new SimpleDateFormat("HH:mm");
        formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    /**
     *
     * @return Returns day of the week
     */

    public String getDayName() {
        date = new Date();
        cal = Calendar.getInstance();
        cal.setTime(date);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        return dayNames.get(dayNum);
    }
}
