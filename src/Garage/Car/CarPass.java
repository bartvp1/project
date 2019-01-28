package Garage.Car;

import Garage.Car.Car;

import java.awt.*;
import java.util.Random;

public class CarPass extends Car {
	private static final Color COLOR=Color.blue;
	
    public CarPass() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesStay(stayMinutes);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
