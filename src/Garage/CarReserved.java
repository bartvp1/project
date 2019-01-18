package Garage;

import java.awt.*;
import java.util.Random;

public class CarReserved extends Car {
    private static final Color COLOR = new Color(0,170,0);

    public CarReserved() {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    public Color getColor(){
        return COLOR;
    }
}
