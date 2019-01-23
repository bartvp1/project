package Summary;

import Garage.GarageModel;
import QueuesSummary.QueueSummaryModel;

public class SummaryController implements Runnable {
    private Thread thread = new Thread(this);
    private boolean running = true;
    private SummaryModel model;
    private QueueSummaryModel queueSummaryModel;
    GarageModel garageModel;

    public SummaryController(SummaryModel model, GarageModel garage, QueueSummaryModel queueSummaryModel) {
        this.model = model;
        this.garageModel = garage;
        this.queueSummaryModel = queueSummaryModel;
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


            queueSummaryModel.setCurrentEntranceSize(garageModel.getEntranceCarQueue().carsInQueue());
            queueSummaryModel.setCurrentExitSize(garageModel.getExitCarQueue().carsInQueue());
            queueSummaryModel.setCurrentPaymentSize(garageModel.getPaymentCarQueue().carsInQueue());
            queueSummaryModel.setMaxEntranceSize(garageModel.getEnterSpeed());
            queueSummaryModel.setMaxExitSize(garageModel.getExitSpeed());
            queueSummaryModel.setMaxPaymentSize(garageModel.getPaymentSpeed());

            queueSummaryModel.update();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
