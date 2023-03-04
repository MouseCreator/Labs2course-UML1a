package com.example.lab23a.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Dates {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
    private static final DateTimeFormatter shortFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     *
     * @return date and time this method is called
     */
    public static LocalDateTime currentDate() {
        return LocalDateTime.now();
    }

    /**
     *
     * @param date - date to parse
     * @return - string in yyyy-MM-dd kk:mm:ss (long date) format
     */
    public static String toDateFormat(LocalDateTime date) {
        return date.format(format);
    }

    /**
     *
     * @param date - date to parse
     * @return - string in yyyy-MM-dd (short date) format
     */
    public static String toDateFormatShort(LocalDateTime date) {
        return date.format(shortFormat);
    }

    /**
     *
     * @param time is string, written in specified date format
     * @return date, parsed from input string
     */
    public static LocalDateTime fromDateFormat(String time) {
        try {
            return LocalDateTime.parse(time, format);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return currentDate();
        }
    }

    /**
     *
     * @param date1 - first date to compare
     * @param date2 - second date to compare
     * @return long number: greater than 0 if date2 is later than date1,
     * less than 0 if date2 is earlier than date1,
     * equals 0, if date1 and date2 are the same to seconds
     */
    public static long compareSeconds(LocalDateTime date1, LocalDateTime date2) {
    	return ChronoUnit.SECONDS.between(date1, date2);
    }

    /**
     *
     * @param date1 is first date to compare
     * @param date2 is second date to compare
     * @return true, if the distance between dates is less than 1 second
     */
    public static boolean equals(LocalDateTime date1, LocalDateTime date2) {
        return ChronoUnit.SECONDS.between(date1, date2) == 0;
    }

    /**
     *
     * @param date - date to parse
     * @return string in yyyy:MM:dd (short date) format
     */
	public static String toDateFormat(LocalDate date) {
		return shortFormat.format(date);
	}

    /**
     *
     * @param string - string in yyyy:MM:dd format
     * @return date in the string, null if failed
     */
	public static LocalDate fromString(String string) {
		 try {
	          return LocalDate.parse(string, shortFormat);
	     } catch (Exception e) {
	          return null;
	     }
	}

    /**
     *
     * @param date1 - first date, not null
     * @param date2 - second date, not null
     * @return number of days passed between dates.
     */
	public static long compareDaysPassed(LocalDate date1, LocalDate date2) {
    	return ChronoUnit.DAYS.between(date2, date1);
    }
}

