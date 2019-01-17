package Graph;

public class GraphController implements Runnable{
    Thread thread = new Thread(this);
    boolean running = false;


    @Override
    public void run() {
        while(running){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
