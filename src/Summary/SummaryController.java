package Summary;

import Garage.GarageModel;
import MyComponents.Controller;
import MyComponents.Model;
import QueuesSummary.QueueSummaryModel;

public class SummaryController extends Controller implements Runnable {
    private Thread thread = new Thread(this);
    private Model queueSummaryModel;
    private Model garageModel;
    private boolean running = false;


    public SummaryController(Model model) {
        super(model);
    }

    public void setGarageModel(Model garageModel) {
        this.garageModel = garageModel;
    }

    public void setQueueSummaryModel(Model queueSummaryModel) {
        this.queueSummaryModel = queueSummaryModel;
    }


    public void start() {
        if (!running) {
            thread = new Thread(this);
            thread.start();
            running = true;
        } else {
            System.out.println("Already running");
        }
    }

    public void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread = null;
        } else {
            System.out.println("Not running");
        }
    }

    @Override
    public void run() {
        while (running) {
            SummaryModel sModel = (SummaryModel) model;
            if (sModel != null) {
                GarageModel gModel = (GarageModel) garageModel;
                sModel.setNormalCars(gModel.getNumberOfNormalCars());
                sModel.setPassCars(gModel.getNumberOfPassCars());
                sModel.setTotalCars(gModel.getTotalCars());
                sModel.setReservedCars(gModel.getNumberOfReservedCars());
                sModel.setReservedLocation(gModel.getNumberOfReservedLocations());
                sModel.setDayName(gModel.getDayName());
                sModel.setHours(gModel.getHour());
                sModel.setMinutes(gModel.getMinute());
                sModel.update();
            }

            QueueSummaryModel qModel = (QueueSummaryModel) queueSummaryModel;
            if (qModel != null) {
                GarageModel gModel = (GarageModel) garageModel;
                qModel.setCurrentEntranceSize(gModel.getEntranceCarQueue().carsInQueue());
                qModel.setCurrentExitSize(gModel.getExitCarQueue().carsInQueue());
                qModel.setCurrentPaymentSize(gModel.getPaymentCarQueue().carsInQueue());
                qModel.setMaxEntranceSize(gModel.getEnterSpeed());
                qModel.setMaxExitSize(gModel.getExitSpeed());
                qModel.setMaxPaymentSize(gModel.getPaymentSpeed());
                qModel.update();
            }


            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
