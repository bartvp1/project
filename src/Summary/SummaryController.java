package Summary;

import Garage.Car.CarNormal;
import Garage.Car.CarPass;
import Garage.Car.CarReserved;
import Garage.GarageModel;
import MyComponents.Controller;
import MyComponents.Model;
import QueuesSummary.QueueSummaryModel;

public class SummaryController extends Controller {
    private Model queueSummaryModel;
    private Model garageModel;

    public SummaryController(Model model) {
        super(model);
    }

    public void setGarageModel(Model garageModel) {
        this.garageModel = garageModel;
    }

    public void setQueueSummaryModel(Model queueSummaryModel) {
        this.queueSummaryModel = queueSummaryModel;
    }

    @Override
    public void run() {
        while (thread != null) {
            SummaryModel sModel = (SummaryModel) model;
            if (sModel != null) {
                GarageModel gModel = (GarageModel) garageModel;
                sModel.setNormalCars(gModel.getCarCount(CarNormal.class));
                sModel.setPassCars(gModel.getCarCount(CarPass.class));
                sModel.setTotalCars(gModel.getCarCount(null));
                sModel.setReservedCars(gModel.getCarCount(CarReserved.class));
                sModel.setReservedLocation(gModel.getNumberOfReservedLocations());
                sModel.setDayName(gModel.getDayName());
                sModel.setHours(gModel.getHour());
                sModel.setMinutes(gModel.getMinute());
                sModel.update();
            }

            QueueSummaryModel qModel = (QueueSummaryModel) queueSummaryModel;
            if (qModel != null) {
                GarageModel gModel = (GarageModel) garageModel;
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
