package Graph;

import Garage.GarageModel;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphModel {
    private boolean fillMode = true;
    private int STARTING_X = 40;
    private boolean nextWeek = false;
    private int currentWeek;

    private int prevDay = 0;
    private HashMap<String, ArrayList<Line2D>> linesMap;
    private HashMap<String, ArrayList<Path2D>> fillMap;
    private ArrayList<Point2D> normalCarPoints;
    private ArrayList<Point2D> passCarPoints;
    private ArrayList<Point2D> totalCarPoints;

    private GraphView graphView;
    private GarageModel garage;

    private Color normalColor = new Color(50, 0, 0, 100);
    private Color totalColor = new Color(0, 50, 0, 100);
    private Color PassColor = new Color(0, 0, 50, 100);

    public GraphModel(GraphView graphView, GarageModel garage) {
        this.graphView = graphView;
        graphView.setModel(this);
        linesMap = new HashMap<>();
        fillMap = new HashMap<>();

        normalCarPoints = new ArrayList<>();
        passCarPoints = new ArrayList<>();
        totalCarPoints = new ArrayList<>();

        linesMap.put("Total", new ArrayList<>());
        linesMap.put("Pass", new ArrayList<>());
        linesMap.put("Normal", new ArrayList<>());

        fillMap.put("Total", new ArrayList<>());
        fillMap.put("Pass", new ArrayList<>());
        fillMap.put("Normal", new ArrayList<>());

        this.garage = garage;
    }

    public Color getColor(String type, boolean filling) {
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
        normalCarPoints.clear();
        totalCarPoints.clear();
        passCarPoints.clear();

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
            Point2D points = new Point2D.Double(prevX, prevY);
            if (str.equals("Normal")) {
                normalCarPoints.add(points);

            } else if (str.equals("Pass")) {
                passCarPoints.add(points);
            } else if (str.equals("Total")) {
                totalCarPoints.add(points);
            }
        }

        if (nextWeek) {
            System.out.println("Resetting grap. PrevX: " + prevX + " nextX: " + nextX);
            reset();
            return;
        }


        double currentX = getX();
        double currentY = getY(value);

        Point2D points = new Point2D.Double(currentX, currentY);
        if (str.equals("Normal")) {
            normalCarPoints.add(points);

        } else if (str.equals("Pass")) {
            passCarPoints.add(points);
        } else if (str.equals("Total")) {
            totalCarPoints.add(points);
        }


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
        double dayWidth = (graphView.getSize().getWidth() - STARTING_X) / 7;
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


    }

    void toggleFillMode() {
        fillMode = !fillMode;
    }

    private ArrayList<Point2D> getNormalCarPoints() {
        return normalCarPoints;
    }

//    public Path2D getPathFill() {
//        double bottomY = graphView.getHeight() - 25 - 10;
//        ArrayList<Line2D> _normalLines = new ArrayList<>(linesMap.get("Normal"));
//        Path2D normalPath = new Path2D.Double();
//        if (_normalLines.size() > 0) {
//
//            Line2D firstLine = _normalLines.get(0);
//            double _x = firstLine.getX1();
//            double _y = firstLine.getY1();
//            normalPath.moveTo(_x, _y);
//            for (Line2D line : _normalLines) {
//                normalPath.lineTo(line.getX2(), line.getY2());
//            }
//            Line2D lastLine = _normalLines.get(_normalLines.size() - 1);
//            normalPath.lineTo(lastLine.getX2(), bottomY);
//            normalPath.lineTo(getStartingX(), bottomY);
//            normalPath.closePath();
//        } else {
//            System.out.println("No Path");
//        }
//
//
//        return normalPath;
//    }
//
//    public Path2D getPassPath() {
//        double bottomY = graphView.getHeight() - 25 - 10;
//        Path2D passPath = new Path2D.Double();
//        ArrayList<Line2D> _passLines = new ArrayList<>(linesMap.get("Pass"));
//        if (_passLines.size() > 0) {
//            Line2D firstLine = _passLines.get(0);
//            double _x = firstLine.getX1();
//            double _y = firstLine.getY1();
//            passPath.moveTo(_x, _y);
//            for (Line2D line : _passLines) {
//                passPath.lineTo(line.getX2(), line.getY2());
//            }
//            Line2D lastLine = _passLines.get(_passLines.size() - 1);
//            passPath.lineTo(lastLine.getX2(), bottomY);
//            passPath.lineTo(getStartingX(), bottomY);
//            passPath.closePath();
//        }
//
//
//        return passPath;
//    }

    public Path2D getPath(String type) {
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
//
//    public Path2D getTotalPath() {
//        double bottomY = graphView.getHeight() - 25 - 10;
//        Path2D normalPath = new Path2D.Double();
//        ArrayList<Line2D> _totalLines = new ArrayList<>(linesMap.get("Total"));
//        if (_totalLines.size() > 0) {
//            Line2D firstLine = _totalLines.get(0);
//
//            double _x = firstLine.getX1();
//            double _y = firstLine.getY1();
//            normalPath.moveTo(_x, _y);
//            for (Line2D line : _totalLines) {
//                normalPath.lineTo(line.getX2(), line.getY2());
//            }
//            Line2D lastLine = _totalLines.get(_totalLines.size() - 1);
//            normalPath.lineTo(lastLine.getX2(), bottomY);
//            normalPath.lineTo(getStartingX(), bottomY);
//            normalPath.closePath();
//        }
//
//
//        return normalPath;
//    }

//    private void addFill(String str, double pX, double pY, double x, double y) {
//        double bottomY = graphView.getHeight() - 25 - 10;
//
//
//        if (str.equals("Normal")) {
//            normalPath = new Path2D.Double();
//            double _x = normalCarPoints.get(0).getX();
//            double _y = normalCarPoints.get(0).getY();
//
//            normalPath.moveTo(_x, _y);
//
//            for (Point2D _point : normalCarPoints) {
//                normalPath.lineTo(_point.getX(), _point.getY());
//            }
//            normalPath.lineTo(x, bottomY);
//            normalPath.lineTo(getStartingX(), bottomY);
//            normalPath.closePath();
//        } else if (str.equals("Pass")) {
//            passPath = new Path2D.Double();
//            double _x = passCarPoints.get(0).getX();
//            double _y = passCarPoints.get(0).getY();
//            passPath.moveTo(_x, _y);
//            for (Point2D _point : passCarPoints) {
//                passPath.lineTo(_point.getX(), _point.getY());
//            }
//            passPath.lineTo(x, bottomY);
//            passPath.lineTo(getStartingX(), bottomY);
//            passPath.closePath();
//        } else if (str.equals("Total")) {
//            totalPath = new Path2D.Double();
//            double _x = totalCarPoints.get(0).getX();
//            double _y = totalCarPoints.get(0).getY();
//            totalPath.moveTo(_x, _y);
//            for (Point2D _point : totalCarPoints) {
//                totalPath.lineTo(_point.getX(), _point.getY());
//            }
//            totalPath.lineTo(x, bottomY);
//            totalPath.lineTo(getStartingX(), bottomY);
//            totalPath.closePath();
//        }
//
////        path.moveTo(pX, pY);
////        path.lineTo(x, y);
////        path.lineTo(x, bottomY);
////        path.lineTo(pX, bottomY);
////        path.closePath();
//
////        ArrayList<Path2D> fills = fillMap.get(str);
//
////        fills.add(path);
//    }

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

    public void setCurrentWeek(int currentWeek) {
        if (this.currentWeek != currentWeek) {
            nextWeek = true;
        }
        this.currentWeek = currentWeek;
    }
}
