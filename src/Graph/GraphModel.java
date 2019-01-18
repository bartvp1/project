package Graph;

import Garage.Garage;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphModel {
    private boolean fillMode = false;
    private int STARTING_X = 40;
    private boolean nextWeek = false;

    private int prevDay = 0;
    private HashMap<String, ArrayList<Line2D>> linesMap;
    private HashMap<String, ArrayList<Path2D>> fillMap;

    private GraphView graphView;
    private Garage garage;

    public GraphModel(GraphView graphView, Garage garage) {
        this.graphView = graphView;
        graphView.setModel(this);
        linesMap = new HashMap<>();
        fillMap = new HashMap<>();


        linesMap.put("Total", new ArrayList<>());
        linesMap.put("Pass", new ArrayList<>());
        linesMap.put("Normal", new ArrayList<>());

        fillMap.put("Total", new ArrayList<>());
        fillMap.put("Pass", new ArrayList<>());
        fillMap.put("Normal", new ArrayList<>());

        this.garage = garage;
    }

    private void reset() {
        linesMap.clear();
        fillMap.clear();

        linesMap.put("Total", new ArrayList<>());
        linesMap.put("Pass", new ArrayList<>());
        linesMap.put("Normal", new ArrayList<>());

        fillMap.put("Total", new ArrayList<>());
        fillMap.put("Pass", new ArrayList<>());
        fillMap.put("Normal", new ArrayList<>());

        nextWeek = false;
        update();
    }

    int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }


    int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    void nextValue(String str, int value) {
        double nextX = getX();
        double prevX;
        double prevY;

        if (!linesMap.get(str).isEmpty()) {
            prevX = linesMap.get(str).get(linesMap.get(str).size() - 1).getX2();
            prevY = linesMap.get(str).get(linesMap.get(str).size() - 1).getY2();
        } else {

            prevX = STARTING_X;
            prevY = getY(value);
        }

        if (nextX < prevX) {
            reset();
            return;
        }


        double currentX = getX();
        double currentY = getY(value);

        addLine(str, prevX, prevY, currentX, currentY);
    }

    private double getY(int value) {
        double _temp = 60.0 / 25.0;
        double bottomY = graphView.getHeight() - 25 - 10;
        return (bottomY - (value / _temp));
    }

    private double getX() {

        prevDay = garage.getDay();
        double sum = 40;
        double dayWidth = graphView.getSize().getWidth() / 7;
        double hourWidth = (dayWidth / 24);
        double minuteWidth = hourWidth / 60;
        sum += dayWidth * garage.getDay();
        sum += hourWidth * garage.getHour();
        sum += minuteWidth * garage.getMinute();

        return sum;
    }

    private void addLine(String str, double pX, double pY, double x, double y) {
        Line2D line = new Line2D.Double(pX, pY, x, y);
        ArrayList<Line2D> lines = linesMap.get(str);
        lines.add(line);

        addFill(str, pX, pY, x, y);
    }

    void toggleFillMode() {
        fillMode = !fillMode;
    }

    private void addFill(String str, double pX, double pY, double x, double y) {
        double bottomY = graphView.getHeight() - 25 - 10;
        Path2D path = new Path2D.Double();
        path.moveTo(pX, pY);
        path.lineTo(x, y);
        path.lineTo(x, bottomY);
        path.lineTo(pX, bottomY);
        path.closePath();

        ArrayList<Path2D> fills = fillMap.get(str);

        fills.add(path);
    }

    ArrayList<Line2D> getLines(String str) {
        return linesMap.get(str);
    }

    ArrayList<Path2D> getFills(String str) {
        return fillMap.get(str);
    }


    int getStartingX() {
        return STARTING_X;
    }

    boolean getFillMode() {
        return fillMode;
    }

    void update() {
        graphView.repaint();
    }

}
