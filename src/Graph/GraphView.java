package Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class GraphView extends JPanel {
    GraphModel model;

    private String[] dagen = {"Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag", "Zaterdag", "Zondag"};

    public GraphView() {
        super(null);
        this.model = model;

    }

    public void setModel(GraphModel model) {
        this.model = model;
    }

    public void init() {
        setBackground(Color.DARK_GRAY);
        setLocation(0, (model.getScreenHeight() / 4) * 3);
        setSize(model.getScreenWidth(), model.getScreenHeight() / 4);
    }

    void drawValueNumbers(Graphics2D g) {

        for (int i = 0; i <= 10; i++) {
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString((10 - i) * 60), 5, (i * 25) - 10);
        }

    }

    void drawGrid(Graphics2D g) {
        //Vertical Lines
        int i = 0;
        int beginX = model.getStartingX();
        double dayWidth = getWidth() / 7;
        int gridSize = 25;
        int beginY = 10;
        int rows = 10;
        for (int x = beginX; x < getWidth(); x += dayWidth) {
            int count = (x - beginX) / gridSize;
            if (x == beginX) {
                g.setStroke(new BasicStroke(3));
                g.setColor(new Color(0, 0, 0, 255));

            } else {
                g.setStroke(new BasicStroke(2));
                g.setColor(new Color(0, 0, 0, 30));
            }

            g.drawLine(x, beginY, x, getHeight() - gridSize - 10);
            g.setColor(Color.WHITE);
            g.drawString(dagen[i], x + (int) dayWidth / 2, getHeight() - 10);
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
            g.drawLine(beginX, beginY + (y * gridSize), getWidth(), beginY + (y * gridSize));
        }
    }

    void drawLines(Graphics2D g) {
        g.setStroke(new BasicStroke(2));
        boolean fillMode = model.getFillMode();
        // All Cars
        g.setColor(new Color(0, 50, 0, 100));

        ArrayList<Line2D> temp = new ArrayList<>(model.getLines("Total"));
        Iterator<Line2D> it = temp.iterator();
        try {
            it.forEachRemaining(g::draw);
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        if (fillMode) {
            g.setColor(new Color(0, 255, 0, 100));
            ArrayList<Path2D> _temp = new ArrayList<>(model.getFills("Total"));
            Iterator<Path2D> _it = _temp.iterator();
            try {
                _it.forEachRemaining(g::fill);
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }

        g.setColor(new Color(100, 0, 0, 255));
        temp = new ArrayList<>(model.getLines("Normal"));
        it = temp.iterator();
        try {
            it.forEachRemaining(g::draw);

        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        if (fillMode) {
            g.setColor(new Color(255, 0, 0, 100));
            ArrayList<Path2D> _temp = new ArrayList<>(model.getFills("Normal"));
            Iterator<Path2D> _it = _temp.iterator();
            try {
                _it.forEachRemaining(g::fill);
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }
        g.setColor(new Color(0, 0, 100, 255));
        temp = new ArrayList<>(model.getLines("Pass"));
        it = temp.iterator();
        try {
            it.forEachRemaining(g::draw);

        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        if (fillMode) {
            g.setColor(new Color(0, 0, 255, 100));
            ArrayList<Path2D> _temp = new ArrayList<>(model.getFills("Pass"));
            Iterator<Path2D> _it = _temp.iterator();
            try {
                _it.forEachRemaining(g::fill);
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }

        g.setColor(Color.BLACK);
        double bottomY = (getHeight() - 25 - 10);
        double x = model.getLines("Normal").get(model.getLines("Normal").size() - 1).getX2();
        Line2D frontLine = new Line2D.Double(x, 10, x, bottomY);
        g.draw(frontLine);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        drawGrid(g2);
        drawValueNumbers(g2);
        drawLines(g2);
    }


}