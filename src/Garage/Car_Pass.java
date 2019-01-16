package Garage;

import java.awt.*;
import java.util.Random;

public class Car_Pass extends Car {
	private static final Color COLOR=Color.blue;
	
    public Car_Pass() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
