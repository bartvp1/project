package Garage;

import Garage.Car.*;
import MyComponents.Model;

import java.util.ArrayList;
import java.util.Random;

public class GarageModel extends Model {
    private int numberOfRows;
    private int numberOfFloors;
    private int numberOfPlaces;
    private int week = 0;
    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    private int numberOfCars = 0;
    private int numberOfPassCars = 0;
    private int numberOfNormalCars = 0;
    private int numberOfReservedCars = 0;
    private int enterSpeed = 3; // number of cars that can enter per minute
    private int maxEntranceQueue = 10;
    private int paymentSpeed = 7; // number of cars that can pay per minute
    private int exitSpeed = 5; // number of cars that can leave per minute

    int weekDayArrivals = 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals = 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour
    int lateOpeningArrivals = 250; // average number of arriving cars per hour
    int lateOpeningPassArrivals = 25; // average number of arriving cars per hour


    private int numberOfOpenSpots;

    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private Car[][][] cars;

    private ArrayList<Location> locations;
    private ArrayList<Reservation> reservations;
    private ArrayList<Location> reservedLocations;

    private GarageView garageView;
    private GarageController controller;
    private int nowTime;

    private int reservedPassLocations = 120;

    public GarageModel(GarageView garageView) {
        this.garageView = garageView;
        this.numberOfRows = 6;
        this.numberOfFloors = 3;
        this.numberOfPlaces = 30;

        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();

        locations = new ArrayList<>();
        reservations = new ArrayList<>();
        reservedLocations = new ArrayList<>();

        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
    }

    public int getNumberOfReservedLocations() {
        return reservedLocations.size();
    }

    public void setController(GarageController controller) {
        this.controller = controller;
    }

    public GarageController getController() {
        return controller;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public void init() {
        addLocations();

    }

    public ArrayList<Location> getReservedLocations() {
        return reservedLocations;
    }

    public void setReservedLocations(ArrayList<Location> reservedLocations) {
        this.reservedLocations = reservedLocations;
    }

    public int getReservedLocationsPass() {
        return reservedPassLocations;
    }

    public void setReservedLocationsPass(int reservedLocationsPass) {
        this.reservedPassLocations = reservedLocationsPass;
    }

    public int getTotalCars() {
        return this.numberOfCars;
    }

    public int getNowTime() {
        return nowTime;
    }

    public void setNowTime(int nowTime) {
        this.nowTime = nowTime;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addToPaymentCarQueue(Car car) {
        paymentCarQueue.addCar(car);
    }

    public void addToEntranceCarQueue(Car car) {
        entranceCarQueue.addCar(car);
    }

    public void addToEntrancePassCarQueue(Car car) {
        entrancePassQueue.addCar(car);
    }

    public void addToExitCarQueue(Car car) {
        exitCarQueue.addCar(car);
    }

    private void addLocations() {
        int id = 0;
        for (int floor = 0; floor < numberOfFloors; floor++) {
            for (int row = 0; row < numberOfRows; row++) {
                for (int place = 0; place < numberOfPlaces; place++) {
                    Location location = new Location(id, floor, row, place);
                    locations.add(location);
                    id++;
                }
            }
        }
    }

    public int getWeek() {
        return week;
    }

    public Location getFirstFreeLocation(String type) {
        int start_at = reservedPassLocations;
        if (type == "PASS") {
            start_at = 0;
        }
        int size = locations.size();
        for (int i = start_at; i < size; i++) {
            Location loc = locations.get(i);
            if (getCarAt(loc) == null) {
                if (reservedLocations.contains(loc)) {
                    if (type == "RESERVED") {
                        return loc;
                    }
                } else if (type != "RESERVED") {
                    return loc;
                }
            }
        }
        return null;
    }

    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }


    private boolean locationIsValid(Location location) {
        if (location == null) {
            return false;
        }
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        int id = location.getId();
        return !(0 > id || id > locations.size());
        //return !(floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces);
    }

    private void reservedForPass() {
        reservedPassLocations = 120;
        if (day == 5 || day == 6) {
            reservedPassLocations = 10;
        }
    }

    void advanceTime() {
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
            week++;
        }
        String day_string;
        switch (day) {
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


        nowTime = (day * 60 * 24) + (hour * 60) + minute;


//        timeLabel.setText(day_string + " " + hour + ":" + minute);
//
//
//        stats.setText(numberOfPassCars + " - " + numberOfNormalCars + " - " + "R_SPOTS: " + reservedLocations.size() + " - " + "R_CARS: " + numberOfReservedCars);

    }


    Car getFirstLeavingCar() {
        int size = locations.size();
        for (int i = 0; i < size; i++) {
            Car car = getCarAt(locations.get(i));
            if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                return car;
            }
        }
        return null;
    }

    void increaseNumberOfPassCarsByOne() {
        this.numberOfPassCars++;
    }

    void decreaseNumberOfPassCarsByOne() {
        this.numberOfPassCars--;
    }

    void increaseNumberOfNormalCarsByOne() {
        this.numberOfNormalCars++;
    }

    void decreaseNumberOfNormalCarsByOne() {
        this.numberOfNormalCars--;
    }

    void increaseNumberOfReservedCarsByOne() {
        this.numberOfReservedCars++;
    }

    void decreaseNumberOfReservedCarsByOne() {
        this.numberOfReservedCars--;
    }

    Car removeCarAt(Location location) {
        Car car = getCarAt(location);
        if (!locationIsValid(location) || car == null) {
            return null;
        }

        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        ////if spot was reserved, remove from reservedLocations
        if (reservedLocations.contains(location)) {
            reservedLocations.remove(location);
        }

        return car;
    }

    public CarQueue getPaymentCarQueue() {
        return paymentCarQueue;
    }

    public CarQueue getExitCarQueue() {
        return exitCarQueue;
    }
//    public CarQueue getReservedCarQueue() {
//        return paymentCarQueue;
//    }

    public void addReservation(int day, int hour, int minute) {
        Reservation reservation = new Reservation(day, hour, minute);
        reservations.add(reservation);
    }


    public int getExitSpeed() {
        return exitSpeed;
    }

    public void addReservedLocation(Location location) {
        this.reservedLocations.add(location);
    }

    public int getPaymentSpeed() {
        return this.paymentSpeed;
    }


    public void updateView() {
        garageView.update(this);
    }

    public CarQueue getEntrancePassQueue() {
        return entrancePassQueue;
    }

    public CarQueue getEntranceCarQueue() {
        return entranceCarQueue;
    }

    public boolean setCarAt(Location location, Car car) {
        Car oldCar = getCarAt(location);
        if (oldCar == null && locationIsValid(location)) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            if (reservedLocations.contains(location)) {
                reservedLocations.remove(location);
            }
            return true;
        }
        return false;
    }

    public String getDayName() {
        String[] days = {"Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag", "Zaterdag", "Zondag"};


        return days[day];
    }

    public int getEnterSpeed() {
        return enterSpeed;
    }

    public int getNumberOfOpenSpots() {
        return numberOfOpenSpots;
    }

    public int getNumberOfCars(String type) {
        int weekDay = (type.equals("PASS")) ? weekDayPassArrivals : weekDayArrivals;
        int weekendDay = (type.equals("PASS")) ? weekendPassArrivals : weekendArrivals;
        int lateOpeningArivals = (type.equals("PASS")) ? lateOpeningPassArrivals : lateOpeningArrivals;



        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5 ? weekDay : weekendDay;

        //If it's a buying night
        if(day == 3)
            averageNumberOfCarsPerHour = lateOpeningArivals;


        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int) Math.round(numberOfCarsPerHour / 60);
    }

    public void addArrivingCars(int numberOfCars, String type) {
        // Add the cars to the back of the queue.

        switch (type) {
            case "NORMAL":
                for (int i = 0; i < numberOfCars; i++) {
                    if (entranceCarQueue.carsInQueue() < getMaxEntranceQueue()) {
                        entranceCarQueue.addCar(new CarNormal());
                    }

                }
                break;
            case "PASS":
                for (int i = 0; i < numberOfCars; i++) {
                    entrancePassQueue.addCar(new CarPass());
                }
                break;
            case "RESERVED":
                entranceCarQueue.addCar(new CarReserved());
                break;
        }

    }

    public void decreaseNumberOfTotalCarsByOne() {
        this.numberOfCars--;
    }

    public void increaseNumberOfTotalCarsByOne() {
        this.numberOfCars++;
    }

    public int getNumberOfNormalCars() {
        return numberOfNormalCars;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    public int getNumberOfPassCars() {
        return numberOfPassCars;
    }

    public int getNumberOfReservedCars() {
        return numberOfReservedCars;
    }

    public int getTickPause() {
        return controller.getTickPause();
    }

    public void setTickPause(int ticks) {
        controller.setTickPause(ticks);
    }

    public void setEnterSpeed(int speed) {
        enterSpeed = speed;
    }

    public int getMaxEntranceQueue() {
        return maxEntranceQueue;
    }

    public void setMaxEntranceQueue(int maxEntranceQueue) {
        this.maxEntranceQueue = maxEntranceQueue;
    }


    public void setExitSpeed(int speed) {
        exitSpeed = speed;
    }

}
