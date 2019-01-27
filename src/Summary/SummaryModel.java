package Summary;

import MyComponents.Model;
import MyComponents.View;

public class SummaryModel extends Model {
    private View summaryView;
    private int totalCars;
    private int passCars;
    private int normalCars;
    private int reservedCars;
    private int reservedLocations;

    int getReservedCars() {
        return reservedCars;
    }

    void setReservedCars(int reservedCars) {
        this.reservedCars = reservedCars;
    }


    private String dayName;
    private int hours;
    private int minutes;

    int getHours() {
        return hours;
    }

    void setHours(int hours) {
        this.hours = hours;
    }

    int getMinutes() {
        return minutes;
    }

    void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    String getDayName() {
        return dayName;
    }

    void setDayName(String dayName) {
        this.dayName = dayName;
    }


    public SummaryModel(View summaryView) {
        this.summaryView = summaryView;
    }


    void update() {
        SummaryView view = (SummaryView) summaryView;
        view.update(this);
    }

    void setTotalCars(int totalCars) {
        this.totalCars = totalCars;
    }

    void setPassCars(int passCars) {
        this.passCars = passCars;
    }

    void setNormalCars(int normalCars) {
        this.normalCars = normalCars;
    }

    int getTotalCars() {
        return totalCars;
    }

    int getPassCars() {
        return passCars;
    }

    int getNormalCars() {
        return normalCars;
    }

    void setReservedLocation(int numberOfReservedLocations) {
        this.reservedLocations = numberOfReservedLocations;
    }

    int getReservedLocations() {
        return reservedLocations;
    }
}
