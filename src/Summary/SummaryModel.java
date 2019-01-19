package Summary;

public class SummaryModel {
    private SummaryView summaryView;
    private int totalCars;
    private int passCars;
    private int normalCars;
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


    public SummaryModel(SummaryView summaryView) {
        this.summaryView = summaryView;
    }


    void update() {
        this.summaryView.update(this);
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
}
