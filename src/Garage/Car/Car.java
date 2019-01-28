package Garage.Car;

import Garage.Location;

import java.awt.*;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private int minutesStay;
    private boolean isPaying;
    private boolean hasToPay;


    public Car() {

    }

    public int getMinutesStay() {
        return minutesStay;
    }

    public void setMinutesStay(int minutesStay) {
        this.minutesStay = minutesStay;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    public boolean getIsPaying() {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public boolean getHasToPay() {
        return hasToPay;
    }

    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    public void tick() {
        minutesLeft--;
    }

    public abstract Color getColor();
}