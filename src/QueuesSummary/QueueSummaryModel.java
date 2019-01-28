package QueuesSummary;

import Garage.GarageModel;
import MyComponents.Model;
import MyComponents.View;

public class QueueSummaryModel extends Model {
    private View view;
    private Model garage;

    public QueueSummaryModel(View view, Model garage) {
        this.view = view;
        this.garage = garage;
    }

    public void update() {
        QueueSummaryView qView = (QueueSummaryView) view;
        qView.update(this);
    }

    public int getEntranceSize(){
        return ((GarageModel)garage).entranceCarQueue.carsInQueue();
    }
    public int getEntrancePassSize(){
        return ((GarageModel)garage).entrancePassQueue.carsInQueue();
    }
    public int getPaymentSize(){
        return ((GarageModel)garage).paymentCarQueue.carsInQueue();
    }
    public int getExitQueue(){
        return ((GarageModel)garage).exitCarQueue.carsInQueue();
    }
}
