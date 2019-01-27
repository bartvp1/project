package MyComponents;

public abstract class Controller implements Runnable {
    protected final Model model;
    protected Thread thread;

    public Controller(Model model) {
        this.model = model;
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }


    public void stop() {
        if (thread != null) {
            thread = null;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
