package Garage;

import Garage.Car.*;

import java.util.Iterator;

public class GarageController implements Runnable {
    Thread thread = new Thread(this);
    private boolean running = true;
    private int ticks = 100;
    private GarageModel garageModel;

    public GarageController(GarageModel garageModel) {
        this.garageModel = garageModel;
    }

    public void init() {
        thread.start();
    }


    public void reserveLocations() {
        Iterator it = garageModel.getReservations().iterator();
        while (it.hasNext()) {
            Reservation res = (Reservation) it.next();
            Boolean done = res.isReserved();

            if (res.getTime() - garageModel.getNowTime() < 60 && !done) {
                Location loc = garageModel.getFirstFreeLocation("NORMAL");
                garageModel.addReservedLocation(loc);
                res.setIsReserved(true);
            }
        }
    }

    public void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    private void carsLeaving() {
        // Let cars leave.
        int i = 0;

        while (garageModel.getExitCarQueue().carsInQueue() > 0 && i < garageModel.getExitSpeed()) {
            garageModel.getExitCarQueue().removeCar();
            i++;
            garageModel.decreaseNumberOfTotalCarsByOne();
        }
    }

    private void carLeavesSpot(Car car) {
        garageModel.removeCarAt(car.getLocation());
        garageModel.addToExitCarQueue(car);
    }

    private void carsPaying() {
        // Let cars pay.
        int i = 0;
        while (garageModel.getPaymentCarQueue().carsInQueue() > 0 && i < garageModel.getPaymentSpeed()) {
            Car car = garageModel.getPaymentCarQueue().removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
            if (car instanceof CarNormal) {
                garageModel.decreaseNumberOfNormalCarsByOne();
            }
            if (car instanceof CarReserved) {
                garageModel.decreaseNumberOfReservedCarsByOne();
            }
        }
    }

    private void carsReadyToLeave() {
        // Add leaving cars to the payment queue.
        Car car = garageModel.getFirstLeavingCar();
        while (car != null) {
            if (car.getHasToPay()) {
                car.setIsPaying(true);
                garageModel.addToPaymentCarQueue(car);
            } else {
                garageModel.removeCarAt(car.getLocation());
                garageModel.addToExitCarQueue(car);
                garageModel.decreaseNumberOfPassCarsByOne();
            }
            car = garageModel.getFirstLeavingCar();
        }
    }

    public void carTick() {
        int size = garageModel.getLocations().size();
        for (int i = 0; i < size; i++) {
            Car car = garageModel.getCarAt(garageModel.getLocations().get(i));
            if (car != null) {
                car.tick();
            }
        }
    }

    private void carsArriving() {
        int numberOfCars = garageModel.getNumberOfCars("NORMAL");
        garageModel.addArrivingCars(numberOfCars, "NORMAL");

        numberOfCars = garageModel.getNumberOfCars("PASS");
        garageModel.addArrivingCars(numberOfCars, "PASS");

        Iterator it = garageModel.getReservations().iterator();
        while (it.hasNext()) {
            Reservation res = (Reservation) it.next();
            Boolean hasReservedSpot = res.isReserved();
            if ((res.getTime() - garageModel.getNowTime()) == 0 && hasReservedSpot && !res.getQueued()) {
                garageModel.addArrivingCars(1, "RESERVED");
                res.setQueued(true);
            }
        }
    }

    public int getTickPause() {
        return this.ticks;
    }

    public void setTickPause(int ticks) {
        this.ticks = ticks;
    }

    public void handleEntrance() {
        carsArriving();
        carsEntering(garageModel.getEntrancePassQueue());
        carsEntering(garageModel.getEntranceCarQueue());
    }

    private void carsEntering(CarQueue queue) {
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.

        while (queue.carsInQueue() > 0 && garageModel.getNumberOfOpenSpots() > 0 && i < garageModel.getEnterSpeed()) {
            Car car = queue.removeCar();
            Location freeLocation;
            if (car instanceof CarPass) {
                freeLocation = garageModel.getFirstFreeLocation("PASS");
                garageModel.increaseNumberOfPassCarsByOne();
            } else if (car instanceof CarReserved) {
                Location loc = garageModel.getFirstFreeLocation("RESERVED");
                freeLocation = loc;
                garageModel.increaseNumberOfReservedCarsByOne();
            } else {
                freeLocation = garageModel.getFirstFreeLocation("NORMAL");
                garageModel.increaseNumberOfNormalCarsByOne();

            }
            garageModel.increaseNumberOfTotalCarsByOne();

            garageModel.setCarAt(freeLocation, car);
            i++;
        }
    }

    @Override
    public void run() {
        while (running) {
            reserveLocations();
            garageModel.advanceTime();
            handleExit();
            carTick();
            garageModel.updateView();
            handleEntrance();
            try {
                Thread.sleep(ticks);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
