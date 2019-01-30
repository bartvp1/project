package CarGraph;

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
            int totalCars = ((GarageModel) garage).getNumberOfPassCars() + ((GarageModel) garage).getNumberOfNormalCars();
            ((GraphModel) model).nextValue("Total", totalCars);
//            ((GraphModel) model).nextValue("Total", ((GarageModel) garage).getTotalCars());
            ((GraphModel) model).nextValue("Pass", ((GarageModel) garage).getNumberOfPassCars());
            ((GraphModel) model).nextValue("Normal", ((GarageModel) garage).getNumberOfNormalCars());
            ((GraphModel) model).setCurrentWeek(((GarageModel) garage).getWeek());
            ((GraphModel) model).update();

            try {
                Thread.sleep(this.ticks);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
