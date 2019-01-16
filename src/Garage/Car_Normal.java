package Garage;

import java.awt.*;
import java.util.Random;

public class Car_Normal extends Car {
	private static final Color COLOR=Color.red;
	
    public Car_Normal() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
