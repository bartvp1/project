package Garage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.util.HashMap;

public class Garage extends JPanel implements Runnable {
    Thread thread = new Thread(this);
    private Dimension size;
    private BufferedImage carParkImage;
    private Car[][][] cars;

    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    private ArrayList<Location> locations;
    private int reservedLocationsPass = 120;


    private int tickPause = 100;

    int weekDayArrivals = 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals = 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute


    int numberOfCars = 0;
    int numberOfPassCars = 0;
    int numberOfNormalCars = 0;
    int numberOfReservedCars = 0;
    int nowTime; //time in seconds


    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    private ArrayList<Location> reservedLocations = new ArrayList<Location>();

    private JLabel timeLabel = new JLabel();
    private JLabel stats = new JLabel();

    public Garage(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfRows = numberOfRows;
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        locations = new ArrayList<Location>();

        timeLabel.setBounds(10, 10, 200, 50);
        timeLabel.setForeground(Color.white);
        add(timeLabel);
        stats.setBounds(10, 120, 200, 50);
        stats.setForeground(Color.white);
        add(stats);
        int x = 2;
        addReservation(0,x,0);
        addReservation(0,x,0);
        x = 3;
        addReservation(0,x,0);
        addReservation(0,x,0);
        addReservation(0,x,0);
    }

    public void init() {
        setBackground(Color.DARK_GRAY);
        size = new Dimension(1500, 750);
        setPreferredSize(size);

        carParkImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        initLocations();

//        repaint();
//        System.out.println(this.carParkImage.getGraphics());

//        System.out.println(i);
//        updateView();
        thread.start();
        repaint();
    }

    private void initLocations(){
        for (int floor = 0; floor < numberOfFloors; floor++) {
            for (int row = 0; row < numberOfRows; row++) {
                for (int place = 0; place < numberOfPlaces; place++) {
                    Location location = new Location(floor, row, place);
                    locations.add(location);
                }
            }
        }
    }

    public void run() {
        while (thread != null) {
            tick();
            try {
                Thread.sleep(tickPause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public int getTotalCars() {
        return this.numberOfCars;
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }

    public void updateView() {
        // Create a new car park image if the size has changed.

        if (carParkImage != null) {
            Graphics graphics = carParkImage.getGraphics();
            int size = locations.size();
            for (int i = 0; i < size; i++) {
                Car car = getCarAt(locations.get(i));
                Color color = car == null ? Color.white : car.getColor();
                if(i<reservedLocationsPass){
                    color = car == null ? new Color(100,180,250) : car.getColor();
                } else{
                    color = car == null ? new Color(255,180,180) : car.getColor();
                }
                for(Location loc : reservedLocations){
                    if(locations.get(i).equals(loc)){
                        color = car == null ? new Color(0xB8B8B8) : car.getColor();
                    }
                }



                drawPlace(graphics, locations.get(i), color);
            }
            repaint();
        }

    }

    private void drawPlace(Graphics graphics, Location location, Color color) {
        int lotWidth = 20;
        int lotHeight = 10;

        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int) Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * lotHeight,
                lotWidth - 1,
                lotHeight - 1); // TODO use dynamic size or constants
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (carParkImage == null) {
            return;
        }
//
//        Dimension currentSize = getSize();
//        if (size.equals(currentSize)) {
//
        g.drawImage(carParkImage, 0, 0, null);
//        }
//        \else {
//            // Rescale the previous image.
//            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
//        }
    }

    private void tick() {
        reserveLocations();
        advanceTime();
        handleExit();
        updateViews();
        handleEntrance();
    }

    private void advanceTime() {
        // Advance the time by one minute
        minute++;
        reservedForPass();
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }
        String day_string;
        switch(day){
            case 0:
                day_string = "Monday";
                break;
            case 1:
                day_string = "Tuesday";
                break;
            case 2:
                day_string = "Wednesday";
                break;
            case 3:
                day_string = "Thursday";
                break;
            case 4:
                day_string = "Friday";
                break;
            case 5:
                day_string = "Saturday";
                break;
            case 6:
                day_string = "Sunday";
                break;
            default:
                day_string = "undefined";
        }
        nowTime = (day*60*24)+(hour*60)+minute;
        timeLabel.setText(day_string + " " + hour + ":" + minute);
        stats.setText("PASS: "+ numberOfPassCars + " ---- " + "NORMAL: " + numberOfNormalCars + " ---- " + "RESERVED: " + numberOfReservedCars);

    }

    public void addReservation(int day, int hour, int minute){
        Reservation reservation = new Reservation(day, hour, minute);
        reservations.add(reservation);
    }

    private void reserveLocations(){
        Iterator it = reservations.iterator();
        while(it.hasNext()){
            Reservation res = (Reservation) it.next();
            Boolean done = (Boolean) res.isReserved();
            if (res.getTime()-nowTime < 45 && !done) {  //reserve a spot 60 min in advance
                Location loc = getFirstFreeLocation("RESERVED");
                reservedLocations.add(loc);
                res.setIsReserved(true);
            }
        }
        System.out.println(reservations.size() +" ---- "+ reservedLocations.size() +" ---- "+ numberOfReservedCars);
    }

    private void reservedForPass(){
        reservedLocationsPass = 120;
        if(day == 5 || day == 6){
            reservedLocationsPass = 10;
        }
    }

    private void handleEntrance() {
        carsArriving();
        carsEntering(entrancePassQueue);
        carsEntering(entranceCarQueue);
    }

    private void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    public void tick2() {
        int size = locations.size();
        for (int i = 0; i < size; i++) {
            Car car = getCarAt(locations.get(i));
            if (car != null) {
                car.tick();
            }
        }
    }

    private void updateViews() {
        tick2();

        updateView();
    }

    private void carsArriving() {
        int numberOfCars = getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, "NORMAL");
        numberOfCars = getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, "PASS");


        Iterator it = reservations.iterator();
        while(it.hasNext()){
            Reservation res = (Reservation) it.next();
            Boolean hasReservedSpot = (Boolean) res.getQueued();
             if(res.getTime()-nowTime <= 0 && hasReservedSpot && !res.getQueued()) {
                 Iterator itr = reservedLocations.iterator();
                 while (itr.hasNext()) {
                     itr.next();
                     addArrivingCars(1, "RESERVED");
                     res.setQueued(true);
                 }
             }
        }
    }

    public Location getFirstFreeLocation(String type) {
        int start_at = reservedLocationsPass;
        if(type == "PASS"){
            start_at = 0;
        }
        int size = locations.size();
        for(int i=start_at;i<size;i++){
            if (getCarAt(locations.get(i)) == null) {
                //System.out.println(reservedLocations.size());
                if(reservedLocations.size()>0){
                    Iterator it = reservedLocations.iterator();
                    while(it.hasNext()){
                        Location loc = (Location) it.next();
                        if(!locations.get(i).equals(loc)){ //return a not reserved spot
                            return locations.get(i);
                        }
                    }
                }
                return locations.get(i);
            }

        }

        return null;
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    private void carsEntering(CarQueue queue) {
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.

        while (queue.carsInQueue() > 0 && numberOfOpenSpots > 0 && i < enterSpeed) {
            Car car = queue.removeCar();
            Location freeLocation;
            if(car instanceof CarPass){
                freeLocation = getFirstFreeLocation("PASS");
            } else{
                freeLocation = getFirstFreeLocation("NORMAL");
            }
            if(car instanceof CarReserved){
                Iterator it = reservedLocations.iterator();
                while(it.hasNext()){
                    Location loc = (Location)it.next();
                    if(loc != null && getCarAt(loc) == null) {
                        freeLocation = loc;
                        it.remove();
                        break;
                    }
                }
            }

            numberOfCars++;
            if (car instanceof CarPass) {
                numberOfPassCars++;
            } else if (car instanceof CarNormal) {
                numberOfNormalCars++;
            } else if(car instanceof CarReserved){
                numberOfReservedCars++;
            }
            setCarAt(freeLocation, car);
            i++;
        }
    }

    public int getNumberOfNormalCars() {
        return numberOfNormalCars;
    }
    public int getNumberOfReservedCars() {
        return numberOfReservedCars;
    }

    public int getNumberOfPassCars() {
        return numberOfPassCars;
    }

    private void carsReadyToLeave() {
        // Add leaving cars to the payment queue.
        Car car = getFirstLeavingCar();
        while (car != null) {
            if (car.getHasToPay()) {
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);

            } else {
                carLeavesSpot(car);
                numberOfPassCars--;
            }
            car = getFirstLeavingCar();
        }
    }

    public Car getFirstLeavingCar() {
        int size = locations.size();
        for (int i = 0; i < size; i++) {
            Car car = getCarAt(locations.get(i));
            if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                return car;
            }
        }
        return null;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }

    private void carsPaying() {
        // Let cars pay.
        int i = 0;
        while (paymentCarQueue.carsInQueue() > 0 && i < paymentSpeed) {
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
            if(car instanceof CarNormal) {
                numberOfNormalCars--;
            }
            if(car instanceof CarReserved){
                numberOfReservedCars--;
            }
        }
    }

    private void carsLeaving() {
        // Let cars leave.
        int i = 0;
        while (exitCarQueue.carsInQueue() > 0 && i < exitSpeed) {
            exitCarQueue.removeCar();
            i++;
            numberOfCars--;
        }
    }

    private int getNumberOfCars(int weekDay, int weekend) {
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5 ? weekDay : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int) Math.round(numberOfCarsPerHour / 60);
    }

    private void addArrivingCars(int numberOfCars, String type) {
        // Add the cars to the back of the queue.

        switch (type) {
            case "NORMAL":
                for (int i = 0; i < numberOfCars; i++) {
                    entranceCarQueue.addCar(new CarNormal());
                }
                break;
            case "PASS":
                for (int i = 0; i < numberOfCars; i++) {
                    entrancePassQueue.addCar(new CarPass());
                }
                break;
            case "RESERVED":
                for (int i = 0; i < numberOfCars; i++) {
                    entranceCarQueue.addCar(new CarReserved());
                }
                break;
        }

    }

    private void carLeavesSpot(Car car) {
        removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
//        System.out.println("Garage.Car left");
    }
}
