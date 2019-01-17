package Garage;

public class Reservation{
    private int day;
    private int hour;
    private int minute;
    private boolean queued = false;
    private boolean reserved = false;

    public Reservation(int day, int hour, int minute) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public int getTime(){
        return (day*60*24)+(hour*60)+minute;
    }
    public boolean getQueued(){ return queued;}
    public boolean isReserved(){ return reserved;}
    public void setIsReserved(boolean bool){ reserved=bool;}
    public void setQueued(boolean bool){ queued=bool;}
}