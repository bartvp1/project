package Graph;

import Garage.Garage;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class GraphScrollContentPanel extends JPanel implements Runnable {
    Thread thread;
    int currentTick = 0;
    int ticks = 250;
    Dimension defaultSize;
    Dimension currentSize;
    int beginX = 10;
    int beginY = 10;
    int gridSize = 25;

    int rows = 10;

    double currentValue = 0;
    double currentNormalValue = 0;
    double getCurrentPassValue = 0;

    double currentX = beginX;
    double currentY = gridSize * (rows - 1) + beginY;
    double currentPassX = beginX;
    double currentPassY = gridSize * (rows - 1) + beginY;
    double currentNormalX = beginX;
    double currentNormalY = gridSize * (rows - 1) + beginY;

    double prevX = currentX;
    double prevY = currentY;
    double prevPassX = currentPassX;
    double prevPassY = currentPassY;
    double prevNormalX = currentNormalX;
    double prevNormalY = currentNormalY;

    Color totalCarColor = new Color(0, 255, 0, 255);
    Color passCarColor = new Color(0, 0, 255, 255);
    Color normalCarColor = new Color(255, 0, 0, 255);

    int step = 5;
    ArrayList<Line2D> lines = new ArrayList<>();
    ArrayList<Line2D> linesPass = new ArrayList<>();
    ArrayList<Line2D> linesNormal = new ArrayList<>();
    Garage garage;

    public GraphScrollContentPanel(Garage garage) {
        this.garage = garage;

    }

    public void init() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);
        int scrollBarHeight = getHorizontalScrollBar().getPreferredSize().height;
        Dimension parentSize = getParent().getParent().getPreferredSize();
        defaultSize = new Dimension(parentSize.width, parentSize.height - scrollBarHeight - 5);
        currentSize = new Dimension(defaultSize);

        setPreferredSize(defaultSize);
        setSize(defaultSize);


        thread = new Thread(this);
        thread.start();

    }

    private JScrollBar getHorizontalScrollBar() {
        JViewport parent = (JViewport) getParent();
        JScrollPane scrollPane = (JScrollPane) parent.getParent();
        return scrollPane.getHorizontalScrollBar();
    }

    void drawGrid(Graphics2D g) {

        //Vertical Lines
        for (int x = beginX; x < currentSize.width; x += gridSize * 5) {
            int count = (x - beginX) / gridSize;
            if (x == beginX) {
                g.setStroke(new BasicStroke(3));
                g.setColor(new Color(0, 0, 0, 255));

            } else {
                g.setStroke(new BasicStroke(2));
                g.setColor(new Color(0, 0, 0, 30));
            }

            g.drawLine(x, beginY, x, currentSize.height - gridSize - 5);
            g.setColor(Color.WHITE);
            if (count % 5 == 0) g.drawString(Integer.toString(count), x - 5, currentSize.height - 10);
        }

        //Horizontal Lines
        for (int y = 0; y < rows; y++) {
            if (y == (rows - 1)) {
                g.setStroke(new BasicStroke(3));
                g.setColor(new Color(0, 0, 0, 255));
            } else {
                g.setStroke(new BasicStroke(2));
                g.setColor(new Color(0, 0, 0, 30));
            }
            g.drawLine(beginX, beginY + (y * gridSize), currentSize.width, beginY + (y * gridSize));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        drawGrid(g2d);
        drawLine(g2d);
    }

    void drawLine(Graphics2D g) {
        g.setStroke(new BasicStroke(2));
        ArrayList<Line2D> temp = new ArrayList<>(lines);
        Iterator<Line2D> it = temp.iterator();
        try {
            g.setColor(totalCarColor);
            it.forEachRemaining(g::draw);

        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }


        temp = new ArrayList<>(linesPass);
        it = temp.iterator();
        try {
            g.setColor(passCarColor);
            it.forEachRemaining(g::draw);

        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        temp = new ArrayList<>(linesNormal);
        it = temp.iterator();

        try {
            g.setColor(normalCarColor);
            it.forEachRemaining(g::draw);

        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        setSize(currentSize.width, currentSize.height);
    }

    private void setNextValue(double value, double pass, double normal) {
        currentValue = value;
        currentNormalValue = normal;
        getCurrentPassValue = pass;

        prevX = currentX;
        prevY = currentY;
        prevNormalX = currentNormalX;
        prevNormalY = currentNormalY;
        prevPassX = currentPassX;
        prevPassY = currentPassY;


        double _temp = 60.0 / 25.0;

        currentY = (235 - (value / _temp));
        currentX += step;

        currentNormalY = (235 - (normal / _temp));
        currentNormalX += step;

        currentPassY = (235 - (pass / _temp));
        currentPassX += step;


        addLine();
        JViewport v = (JViewport) getParent();
        JScrollPane p = (JScrollPane) v.getParent();
        JScrollBar b = p.getHorizontalScrollBar();

        if (currentX > currentSize.width) {
            currentSize.width += step;
            setPreferredSize(currentSize);
            b.setValue(b.getMaximum());
        }

        v.updateUI();
    }

    void addLine() {
        Line2D line = new Line2D.Double(prevX, prevY, currentX, currentY);
        Line2D linePass = new Line2D.Double(prevPassX, prevPassY, currentPassX, currentPassY);
        Line2D lineNormal = new Line2D.Double(prevNormalX, prevNormalY, currentNormalX, currentNormalY);

        lines.add(line);
        linesNormal.add(lineNormal);
        linesPass.add(linePass);

        repaint();

    }

    int getCurrentCarAmount() {
        return garage.getTotalCars();
    }

    int getCurrentPassCarAmount() {
        return garage.getNumberOfPassCars();
    }

    int getCurrentNormalCarAmount() {
        return garage.getNumberOfNormalCars();
    }

    public void setTicks(int ticks){
        this.ticks = ticks;
    }
    @Override
    public void run() {
        while (thread != null) {
            currentTick++;

            if (currentTick >= ticks) {

                double scale = (double) getCurrentCarAmount();
                double scalePass = (double) getCurrentPassCarAmount();
                double scaleNormal = (double) getCurrentNormalCarAmount();

                setNextValue(scale, scalePass, scaleNormal);

                currentTick = 0;
            }
            repaint();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void stop() {
        thread = null;
    }

    private void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }

    }

    public void reset() {
        boolean playing = (thread != null);
        stop();
        currentSize = new Dimension(defaultSize);
        setPreferredSize(currentSize);

        lines.clear();
        linesNormal.clear();
        linesPass.clear();

        JViewport v = (JViewport) getParent();
        JScrollPane p = (JScrollPane) v.getParent();
        JScrollBar b = p.getHorizontalScrollBar();
        b.setValue(b.getMinimum());

        v.updateUI();

        currentX = beginX;
        currentNormalX = beginX;
        currentPassX = beginX;

        if (playing) {
            start();
        }
        repaint();
    }
}
