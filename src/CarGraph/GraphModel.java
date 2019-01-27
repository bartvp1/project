package CarGraph;

import Garage.GarageModel;
import MyComponents.Model;
import MyComponents.View;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphModel extends Model {
    private boolean fillMode = true;
    private int STARTING_X = 40;
    private boolean nextWeek = false;
    private int currentWeek;

    private HashMap<String, ArrayList<Line2D>> linesMap;


    private View graphView;
    private Model garageModel;

    public GraphModel(View graphView, Model garage) {
        this.graphView = graphView;
        ((GraphView) graphView).setModel(this);

        linesMap = new HashMap<>();

        linesMap.put("Total", new ArrayList<>());
        linesMap.put("Pass", new ArrayList<>());
        linesMap.put("Normal", new ArrayList<>());

        this.garageModel = garage;
    }

    Color getColor(String type, boolean filling) {
        int r = 0;
        int g = 0;
        int b = 0;
        int a = 100;
        switch (type) {
            case "Normal":
                r = 50;
                break;
            case "Pass":
                b = 50;
                break;
            case "Total":
                g = 50;
                break;
        }

        if (filling) {
            r *= 5;
            g *= 5;
            b *= 5;
        }

        return new Color(r, g, b, a);
    }

    private void reset() {
        linesMap.clear();
        linesMap.put("Total", new ArrayList<>());
        linesMap.put("Pass", new ArrayList<>());
        linesMap.put("Normal", new ArrayList<>());

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
        double prevX;
        double prevY;

        if (!linesMap.get(str).isEmpty()) {
            prevX = linesMap.get(str).get(linesMap.get(str).size() - 1).getX2();
            prevY = linesMap.get(str).get(linesMap.get(str).size() - 1).getY2();
        } else {
            prevX = STARTING_X;
            prevY = getY(value);
        }

        if (nextWeek) {
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
        double sum = 40;
        double dayWidth = (graphView.getSize().getWidth() - STARTING_X) / 7;
        double hourWidth = (dayWidth / 24);
        double minuteWidth = hourWidth / 60;
        sum += dayWidth * ((GarageModel) garageModel).getDay();
        sum += hourWidth * ((GarageModel) garageModel).getHour();
        sum += minuteWidth * ((GarageModel) garageModel).getMinute();

        return sum;
    }

    private void addLine(String str, double pX, double pY, double x, double y) {
        Line2D line = new Line2D.Double(pX, pY, x, y);
        ArrayList<Line2D> lines = linesMap.get(str);
        lines.add(line);
    }

    void toggleFillMode() {
        fillMode = !fillMode;
    }

    Path2D getPath(String type) {
        double bottomY = graphView.getHeight() - 25 - 10;
        Path2D normalPath = new Path2D.Double();
        ArrayList<Line2D> _totalLines = new ArrayList<>(linesMap.get(type));

        if (_totalLines.size() > 0) {
            Line2D firstLine = _totalLines.get(0);

            double _x = firstLine.getX1();
            double _y = firstLine.getY1();
            normalPath.moveTo(_x, _y);
            for (Line2D line : _totalLines) {
                normalPath.lineTo(line.getX2(), line.getY2());
            }
            Line2D lastLine = _totalLines.get(_totalLines.size() - 1);
            normalPath.lineTo(lastLine.getX2(), bottomY);
            normalPath.lineTo(getStartingX(), bottomY);
            normalPath.closePath();
        }
        return normalPath;
    }


    ArrayList<Line2D> getLines(String str) {
        return linesMap.get(str);
    }

    int getStartingX() {
        return STARTING_X;
    }

    boolean getFillMode() {
        return fillMode;
    }

    void update() {
        ((GraphView) graphView).update(this);
    }

    void setCurrentWeek(int currentWeek) {
        if (this.currentWeek != currentWeek) {
            nextWeek = true;
        }
        this.currentWeek = currentWeek;
    }


}
