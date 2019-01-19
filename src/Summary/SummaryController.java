package Summary;

import Garage.Garage;

public class SummaryController implements Runnable {
    private Thread thread = new Thread(this);
    private boolean running = true;
    private SummaryModel model;
    private Garage garage;

    public SummaryController(SummaryModel model, Garage garage) {
        this.model = model;
        this.garage = garage;
    }

    public void init() {
        thread.start();
    }

    @Override
    public void run() {
        while (running) {
            model.setNormalCars(garage.getNumberOfNormalCars());
            model.setPassCars(garage.getNumberOfPassCars());
            model.setTotalCars(garage.getTotalCars());
            model.setDayName(garage.getDayName());
            model.setHours(garage.getHour());
            model.setMinutes(garage.getMinute());

            model.update();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
