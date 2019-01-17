package Graph;

import Garage.Garage;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class GraphScrollContentPanel extends JPanel implements Runnable {
    Thread thread;
    int currentTick = 0;
    int ticks = 5;
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

    int step = 1;
    ArrayList<Line2D> lines = new ArrayList<>();
    ArrayList<Line2D> linesPass = new ArrayList<>();
    ArrayList<Line2D> linesNormal = new ArrayList<>();

    ArrayList<Path2D> pLines = new ArrayList<>();
    ArrayList<Path2D> pLinesPass = new ArrayList<>();


    ArrayList<Path2D> pLinesNormal = new ArrayList<>();


    Garage garage;

    private String[] dagen = {"Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag", "Zaterdag", "Zondag"};

    private double dayWidth;
    private boolean fillMode = true;
    private boolean repeat = true;
    private boolean working = true;

    boolean nextWeek = false;
    int prevDay = 0;


//    Color totalCarColor =

    public GraphScrollContentPanel(Garage garage) {
        this.garage = garage;

    }

    public void toggleFillMode() {
        this.fillMode = !this.fillMode;
    }

    void init() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);
        int scrollBarHeight = getHorizontalScrollBar().getPreferredSize().height;
        Dimension parentSize = getParent().getParent().getPreferredSize();
        defaultSize = new Dimension(parentSize.width, parentSize.height - scrollBarHeight - 5);

        this.dayWidth = defaultSize.width / 7;


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
        int i = 0;
        for (int x = beginX; x < currentSize.width; x += this.dayWidth) {
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
            g.drawString(dagen[i], x + (int) this.dayWidth / 2, currentSize.height - 10);
            i++;
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

        // All Cars
        g.setColor(new Color(0, 50, 0, 100));

        ArrayList<Line2D> temp = new ArrayList<>(lines);
        Iterator<Line2D> it = temp.iterator();
        try {
            it.forEachRemaining(g::draw);
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        if (fillMode) {
            g.setColor(new Color(0, 255, 0, 100));
            ArrayList<Path2D> _temp = new ArrayList<>(pLines);
            Iterator<Path2D> _it = _temp.iterator();
            try {
                _it.forEachRemaining(g::fill);
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }

        g.setColor(new Color(100, 0, 0, 255));

        temp = new ArrayList<>(linesNormal);
        it = temp.iterator();

        try {

            it.forEachRemaining(g::draw);

        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        if (fillMode) {
            g.setColor(new Color(255, 0, 0, 100));
            ArrayList<Path2D> _temp = new ArrayList<>(pLinesNormal);
            Iterator<Path2D> _it = _temp.iterator();
            try {
                _it.forEachRemaining(g::fill);
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }
        g.setColor(new Color(0, 0, 100, 255));
        temp = new ArrayList<>(linesPass);
        it = temp.iterator();
        try {
            it.forEachRemaining(g::draw);

        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        if (fillMode) {
            g.setColor(new Color(0, 0, 255, 100));
            ArrayList<Path2D> _temp = new ArrayList<>(pLinesPass);
            Iterator<Path2D> _it = _temp.iterator();
            try {
                _it.forEachRemaining(g::fill);
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }

        g.setColor(Color.BLACK);
        double bottomY = (currentSize.height - gridSize - 5);
        Line2D frontLine = new Line2D.Double(currentX, beginY, currentX, bottomY);
        g.draw(frontLine);

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


        // 60 is hoeveel tussen elke horizontale lijn aan waarde heeft, 25 is de pixels tussen de waardes
        double _temp = 60.0 / 25.0;
        if (prevDay > garage.getDay()) {
            nextWeek = true;
        }
        prevDay = garage.getDay();
        double sum = beginX;
        double hourWidth = (dayWidth / 24);
        double minuteWidth = hourWidth / 60;
        sum += dayWidth * garage.getDay();
        sum += hourWidth * garage.getHour();
        sum += minuteWidth * garage.getMinute();

        currentY = (235 - (value / _temp));
        currentX = sum;

        currentNormalY = (235 - (normal / _temp));

        currentNormalX = sum;
        currentPassY = (235 - (pass / _temp));


        currentPassX = sum;

        JViewport v = (JViewport) getParent();
        JScrollPane p = (JScrollPane) v.getParent();
        JScrollBar b = p.getHorizontalScrollBar();

        addLine();
        v.updateUI();

    }


    void addLine() {
        double bottomY = (currentSize.height - gridSize - 5);

        // TOTAL CARS FILL
        Path2D path = new Path2D.Double();
        path.moveTo(prevX, prevY);
        path.lineTo(currentX, currentY);
        path.lineTo(currentX, bottomY);
        path.lineTo(prevX, bottomY);
        path.closePath();
        pLines.add(path);

        // PASS CARS FILL
        Path2D path2 = new Path2D.Double();
        path2.moveTo(prevPassX, prevPassY);
        path2.lineTo(currentPassX, currentPassY);
        path2.lineTo(currentPassX, bottomY);
        path2.lineTo(prevPassX, bottomY);
        path2.closePath();
        pLinesPass.add(path2);

        // NORMAL CARS FILL
        Path2D path3 = new Path2D.Double();
        path3.moveTo(prevNormalX, prevNormalY);
        path3.lineTo(currentNormalX, currentNormalY);
        path3.lineTo(currentNormalX, bottomY);
        path3.lineTo(prevNormalX, bottomY);
        path3.closePath();
        pLinesNormal.add(path3);

        Line2D line = new Line2D.Double(prevX, prevY, currentX - 1, currentY);
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


    public void setTicks(int ticks) {

        this.ticks = ticks;
    }

    @Override
    public void run() {

        while (thread != null) {
            currentTick++;

            if (currentTick >= ticks) {

                while (thread != null && this.working) {
                    double scale = (double) getCurrentCarAmount();
                    double scalePass = (double) getCurrentPassCarAmount();
                    double scaleNormal = (double) getCurrentNormalCarAmount();


                    if (nextWeek) {
                        prevX = beginX;
                        prevNormalX = beginX;
                        prevPassX = beginX;

                        lines.clear();
                        linesNormal.clear();
                        linesPass.clear();

                        pLines.clear();
                        pLinesNormal.clear();
                        pLinesPass.clear();

                        nextWeek = false;
                    }

                    currentTick = 0;
                }
                repaint();

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
        }

    }

    private void stop() {
        thread = null;
    }

    private void start() {
        if (thread == null) {
            thread = new Thread(this);
//            working = true;
            thread.start();
        }

    }

    public void reset() {
        boolean playing = (thread != null);


//        working = false;

        thread = null;

        currentSize = new Dimension(defaultSize);
        setPreferredSize(currentSize);
        lines = new ArrayList<>();
        linesPass = new ArrayList<>();
        linesNormal = new ArrayList<>();

        pLines = new ArrayList<>();
        pLinesPass = new ArrayList<>();

        lines.clear();
        linesNormal.clear();
        linesPass.clear();

        pLines.clear();
        pLinesNormal.clear();
        pLinesPass.clear();


        JViewport v = (JViewport) getParent();
        JScrollPane p = (JScrollPane) v.getParent();
        JScrollBar b = p.getHorizontalScrollBar();
        b.setValue(b.getMinimum());

        v.updateUI();

        currentX = beginX;
        currentNormalX = beginX;
        currentPassX = beginX;

        prevX = beginX;
        prevNormalX = beginX;
        prevPassX = beginX;


        if (playing) {
            System.out.println("Starting");
            start();
        }
        repaint();
    }
}
