package Graph;

import Garage.Garage;

public class GraphController implements Runnable {
    private int ticks = 25;
    private Thread thread = new Thread(this);
    private boolean running = true;

    private GraphModel graphModel;
    private Garage garage;

    public GraphController(GraphModel graphModel, Garage garage) {
        this.graphModel = graphModel;
        this.garage = garage;
    }
    public void toggleFillMode(){
        graphModel.toggleFillMode();
    }
    public void setTicks(int ticks){
        this.ticks = ticks;

    }
    public int getTicks(){
        return ticks;
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
                Thread.sleep(this.ticks);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
