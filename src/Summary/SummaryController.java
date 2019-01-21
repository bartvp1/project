package Summary;

import Garage.GarageModel;

public class SummaryController implements Runnable {
    private Thread thread = new Thread(this);
    private boolean running = true;
    private SummaryModel model;
    GarageModel garageModel;

    public SummaryController(SummaryModel model, GarageModel garage) {
        this.model = model;
        this.garageModel = garage;
    }

    public void init() {
        thread.start();
    }

    @Override
    public void run() {
        while (running) {

            model.setNormalCars(garageModel.getNumberOfNormalCars());
            model.setPassCars(garageModel.getNumberOfPassCars());
            model.setTotalCars(garageModel.getTotalCars());
            model.setReservedCars(garageModel.getNumberOfReservedCars());
            model.setReservedLocation(garageModel.getNumberOfReservedLocations());
            model.setDayName(garageModel.getDayName());
            model.setHours(garageModel.getHour());
            model.setMinutes(garageModel.getMinute());


            model.update();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
