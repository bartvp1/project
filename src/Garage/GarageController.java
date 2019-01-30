package Garage;

import Finance.FinanceView;
import Garage.Car.*;
import MyComponents.Controller;
import MyComponents.Model;

import java.util.Iterator;

public class GarageController extends Controller {
    private int ticks = 100;
    private FinanceView fv;
    private double moneyEarned = 0;
    private double priceRegular = 1.45;
    private double priceReservation = 1.20;

    public GarageController(Model model, FinanceView finance) {
        super(model);
        fv = finance;
    }

    public void setMoneyEarned(double moneyEarned) {
        this.moneyEarned = moneyEarned;
    }

    public void setPriceRegular(double priceRegular) {
        this.priceRegular = priceRegular;
    }

    public void setPriceReservation(double priceReservation) {
        this.priceReservation = priceReservation;
    }

    private void reserveLocations() {
        Iterator it = ((GarageModel) model).getReservations().iterator();
        while (it.hasNext()) {
            Reservation res = (Reservation) it.next();
            Boolean done = res.isReserved();
            int deltaTime = res.getTime() - ((GarageModel) model).getNowTime();
            if (deltaTime < 60 && deltaTime > 0 && !done) {
                Location loc = ((GarageModel) model).getFirstFreeLocation("NORMAL");
                ((GarageModel) model).addReservedLocation(loc);
                res.setIsReserved(true);
            }
        }
    }

    private void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    private void carsLeaving() {
        // Let cars leave.
        int i = 0;

        while (((GarageModel) model).getExitCarQueue().carsInQueue() > 0 && i < ((GarageModel) model).getExitSpeed()) {
            ((GarageModel) model).getExitCarQueue().removeCar();
            i++;
        }


    }

    private void carLeavesSpot(Car car) {
        ((GarageModel) model).removeCarAt(car.getLocation());
        ((GarageModel) model).addToExitCarQueue(car);

    }

    private void carsPaying() {
        // Let cars pay.
        int i = 0;
        while (((GarageModel) model).getPaymentCarQueue().carsInQueue() > 0 && i < ((GarageModel) model).getPaymentSpeed()) {
            Car car = ((GarageModel) model).getPaymentCarQueue().removeCar();
            // TODO Handle payment.


            carLeavesSpot(car);
            i++;
            if (car instanceof CarNormal) {
                ((GarageModel) model).decreaseNumberOfNormalCarsByOne();
                moneyEarned += priceRegular;
            }
            if (car instanceof CarReserved) {
                ((GarageModel) model).decreaseNumberOfReservedCarsByOne();
                moneyEarned += priceReservation;
            }
        }
    }

    private void carsReadyToLeave() {
        // Add leaving cars to the payment queue.
        Car car = ((GarageModel) model).getFirstLeavingCar();
        while (car != null) {
            if (car.getHasToPay()) {
                car.setIsPaying(true);
                ((GarageModel) model).addToPaymentCarQueue(car);
            } else {
                ((GarageModel) model).removeCarAt(car.getLocation());
                ((GarageModel) model).addToExitCarQueue(car);
                ((GarageModel) model).decreaseNumberOfPassCarsByOne();
            }
            car = ((GarageModel) model).getFirstLeavingCar();
        }
    }

    private void carTick() {
        int size = ((GarageModel) model).getLocations().size();
        for (int i = 0; i < size; i++) {
            Car car = ((GarageModel) model).getCarAt(((GarageModel) model).getLocations().get(i));
            if (car != null) {
                car.tick();
            }
        }
    }

    private void carsArriving() {
        int numberOfCars = ((GarageModel) model).getNumberOfCars("NORMAL");
        ((GarageModel) model).addArrivingCars(numberOfCars, "NORMAL");

        numberOfCars = ((GarageModel) model).getNumberOfCars("PASS");
        ((GarageModel) model).addArrivingCars(numberOfCars, "PASS");

        Iterator it = ((GarageModel) model).getReservations().iterator();
        while (it.hasNext()) {
            Reservation res = (Reservation) it.next();
            Boolean hasReservedSpot = res.isReserved();
            if ((res.getTime() - ((GarageModel) model).getNowTime()) == 0 && hasReservedSpot && !res.getQueued()) {
                ((GarageModel) model).addArrivingCars(1, "RESERVED");
                res.setQueued(true);
            }
        }
    }

    int getTickPause() {
        return this.ticks;
    }

    void setTickPause(int ticks) {
        this.ticks = ticks;
    }

    private void handleEntrance() {
        carsArriving();
        carsEntering(((GarageModel) model).getEntrancePassQueue());
        carsEntering(((GarageModel) model).getEntranceCarQueue());
    }

    private void carsEntering(CarQueue queue) {
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.

        while (queue.carsInQueue() > 0 && ((GarageModel) model).getNumberOfOpenSpots() > 0 && i < ((GarageModel) model).getEnterSpeed()) {
            Car car = queue.removeCar();
            Location freeLocation;
            if (car instanceof CarPass) {
                freeLocation = ((GarageModel) model).getFirstFreeLocation("PASS");
                ((GarageModel) model).increaseNumberOfPassCarsByOne();
            } else if (car instanceof CarReserved) {
                Location loc = ((GarageModel) model).getFirstFreeLocation("RESERVED");
                freeLocation = loc;
                ((GarageModel) model).increaseNumberOfReservedCarsByOne();
            } else {
                freeLocation = ((GarageModel) model).getFirstFreeLocation("NORMAL");
                ((GarageModel) model).increaseNumberOfNormalCarsByOne();

            }
            ((GarageModel) model).increaseNumberOfTotalCarsByOne();

            ((GarageModel) model).setCarAt(freeLocation, car);
            i++;
        }
    }

    public double getPriceRegular() {
        return priceRegular;
    }

    public double getPriceReservation() {
        return priceReservation;
    }

    public double getMoneyEarned() {
        return moneyEarned;
    }


    @Override
    public void run() {
        while (thread != null) {
            reserveLocations();
            ((GarageModel) model).advanceTime();
            handleExit();
            carTick();
            ((GarageModel) model).updateView();
            fv.update(this);
            handleEntrance();
            try {
                Thread.sleep(ticks);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
