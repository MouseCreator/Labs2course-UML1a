package com.example.lab23a.model;

import java.time.LocalDate;

public class UserStreak {

    public UserStreak() {
        this.userStreakInDays = 0;
        this.lastStudiedDate = Dates.currentDate().toLocalDate();
    }
    private LocalDate lastStudiedDate;
    private int userStreakInDays;
    /**
     *
     * @return date, when user studied last time
     */
    public LocalDate getLastStudied() {
        return lastStudiedDate;
    }
    /**
     *
     * @param date - date, when user studied last time
     */
    public void setLastStudied(LocalDate date) {
        lastStudiedDate = date;
    }

    /**
     * Sets last studied dae to current date
     */
    private void updateLastStudiedDate() {
        this.lastStudiedDate = Dates.currentDate().toLocalDate();
    }
    /**
     * Sets streak to 0 if user skipped a day of studying
     */
    public void initStreak() {
        if (lastStudiedDate == null) {
            lastStudiedDate = Dates.currentDate().toLocalDate();
            this.userStreakInDays = 0;
        }
        else if (Dates.compareDaysPassed(Dates.currentDate().toLocalDate(), lastStudiedDate) > 1) {
            this.userStreakInDays = 0;
        }
    }
    /**
     * Increments user's streak if it hasn't been done today
     */
    public void updateStreak() {
        if (Dates.compareDaysPassed(Dates.currentDate().toLocalDate(), lastStudiedDate) == 1 || userStreakInDays == 0) {
            updateLastStudiedDate();
            userStreakInDays++;
        }
    }
    /**
     *
     * @return number of days that user has studied in a row
     */
    public int getStreak() {
        return userStreakInDays;
    }
    public void setStreak(int days) {
        this.userStreakInDays = days;
    }
    /**
     *
     * @return empty string if user doesn't have a streak or a message that states how many days in a row user have
     */
    public String getStreakText() {
        if (userStreakInDays == 0)
            return "";
        else
            return "You are on a " + userStreakInDays + "-day streak!";
    }
}
