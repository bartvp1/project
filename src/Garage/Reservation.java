package Garage;

import java.util.ArrayList;

public class Reservation{
    private int day;
    private int hour;
    private int minute;

    public Reservation(int day, int hour, int minute) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public int getDay() {
        return this.day;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }
}