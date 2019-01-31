package CarGraph;

import Garage.Car.CarNormal;
import Garage.Car.CarPass;
import Garage.GarageModel;
import MyComponents.Controller;
import MyComponents.Model;

public class GraphController extends Controller {
    private int ticks = 10;
    private Model garage;

    public GraphController(Model model) {
        super(model);
    }

    public void setGarage(Model garage) {
        this.garage = garage;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    @Override
    public void run() {

        while (thread != null) {
            ((GraphModel) model).nextValue("Total", ((GarageModel) garage).getCarCount(null));
            ((GraphModel) model).nextValue("Pass", ((GarageModel) garage).getCarCount(CarPass.class));
            ((GraphModel) model).nextValue("Normal", ((GarageModel) garage).getCarCount(CarNormal.class));
            ((GraphModel) model).setCurrentWeek(((GarageModel) garage).getWeek());
            ((GraphModel) model).update();

            try {
                Thread.sleep(ticks);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
