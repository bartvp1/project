package Garage;

import MyComponents.Model;

public class FinancesController extends Model {
    int totalCars;

    double priceReservation;
    double pricePassHolders;
    double priceNormal;

    public FinancesController()
    {
         priceReservation = 2.5;
         pricePassHolders = 0;
         priceNormal = 3;
    }

    public int getTotalCars() {
        return totalCars;
    }

    public double getPriceReservation() {
        return priceReservation;
    }

    public double getPricePassHolders() {
        return pricePassHolders;
    }

    public double getPriceNormal() {
        return priceNormal;
    }

    public void setTotalCars(int totalCars) {
        this.totalCars = totalCars;
    }

    public void setPriceReservation(double priceReservation) {
        this.priceReservation = priceReservation;
    }

    public void setPricePassHolders(double pricePassHolders) {
        this.pricePassHolders = pricePassHolders;
    }

    public void setPriceNormal(double priceNormal) {
        this.priceNormal = priceNormal;
    }
}
