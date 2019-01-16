package Garage;

public class Reservation{
    private int day;
    private int hour;
    private int minute;

    public Reservation(int day, int hour, int minute) {
        this.day = day;//day 0 is monday
        this.hour = hour;
        this.minute = minute;
    }

    public int getMinutesFromStart() {
        return (day*60*24)+(hour*60)+minute;
    }
}