package Graph;

import Garage.Garage;

public class GraphController implements Runnable {
    public int ticks = 5;
    Thread thread = new Thread(this);
    boolean running = true;

    GraphModel graphModel;
    Garage garage;

    public GraphController(GraphModel graphModel, Garage garage) {
        this.graphModel = graphModel;
        this.garage = garage;
    }

    public void init() {
        thread.start();
    }

    @Override
    public void run() {
        while (running) {
            graphModel.nextValue("Total", garage.getTotalCars());
            graphModel.nextValue("Pass", garage.getNumberOfPassCars());
            graphModel.nextValue("Normal", garage.getNumberOfNormalCars());

            graphModel.update();

            try {
                Thread.sleep(ticks);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
