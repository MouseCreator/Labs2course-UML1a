package com.example.lab23a.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Dates {
    public static LocalDateTime currentDate() {
        return LocalDateTime.now();
    }
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
    private static final DateTimeFormatter shortFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  
    public static String toDateFormat(LocalDateTime date) {
        return date.format(format);
    }
    public static String toDateFormatShort(LocalDateTime date) {
        return date.format(shortFormat);
    }
    public static LocalDateTime fromDateFormat(String time) {
        try {
            return LocalDateTime.parse(time, format);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return currentDate();
        }
    }

    public static long compare(LocalDateTime date1, LocalDateTime date2) {
    	return ChronoUnit.SECONDS.between(date1, date2);
    }
    public static boolean equals(LocalDateTime date1, LocalDateTime date2) {
        return ChronoUnit.SECONDS.between(date1, date2) == 0;
    }
	public static String toDateFormat(LocalDate date) {
		return shortFormat.format(date);
	}
	public static LocalDate fromString(String string) {
		 try {
	          return LocalDate.parse(string, shortFormat);
	     } catch (Exception e) {
	          return null;
	     }
	}
	public static long compareDaysPassed(LocalDate date1, LocalDate date2) {
    	return ChronoUnit.DAYS.between(date2, date1);
    }
}

