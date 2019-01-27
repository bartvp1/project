package CarGraph;

import Garage.GarageModel;
import MyComponents.Controller;
import MyComponents.Model;

public class GraphController extends Controller implements Runnable {
    private int ticks = 10;
    private Thread thread = new Thread(this);
    private boolean running = true;
    private Model garage;


    public GraphController(Model model) {
        super(model);
    }

    public void setGarage(Model garage) {
        this.garage = garage;
    }

    public void toggleFillMode() {
        ((GraphModel) model).toggleFillMode();
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;

    }

    public void init() {
        thread.start();
    }

    @Override
    public void run() {

        while (running) {
            ((GraphModel) model).nextValue("Total", ((GarageModel) garage).getTotalCars());
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
