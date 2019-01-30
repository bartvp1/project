package CarGraph;

import Garage.GarageModel;
import Garage.Location;
import MyComponents.Model;
import MyComponents.View;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Graph Model
 * Het model van de Graph Panel
 * Hier worden de lijnen(Line2D) opgeslagen in een HashMap(linesMap)
 * De kleuren, vulling en posities worden vanuit hier opgehaald.
 */
public class GraphModel extends Model {
    // De linker margin van de grafiek zelf
    // 40 omdat er dan nog genoeg ruimte is voor de waardes van de y-as
    private int STARTING_X = 40;


    private boolean nextWeek = false;
    private int currentWeek;

    private HashMap<String, ArrayList<Line2D>> linesMap;


    private View graphView;
    private Model garageModel;

    /**
     * @param graphView de view van deze component
     * @param garage    een referentie naar garage
     *                  De hashmaps worden hier geinitialiseerd
     */
    public GraphModel(View graphView, Model garage) {
        this.graphView = graphView;
        ((GraphView) graphView).setModel(this);

        linesMap = new HashMap<>();

        linesMap.put("Total", new ArrayList<>());
        linesMap.put("Pass", new ArrayList<>());
        linesMap.put("Normal", new ArrayList<>());

        this.garageModel = garage;
    }


    /**
     * @param type    String die aangeeft welk type het gaat; Normal(reguliere), Pass(Pashouders) en Total
     * @param filling boolean of het een vulling betreft of niet, zoja dan wordt de kleur duidelijker
     * @return De color van de vulling of line
     */
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

    /**
     * Dit wordt aangeroepen als de week opnieuw begint.
     * De hashmap worden geleegd en opniew aangemaakt.
     */
    private void reset() {
        linesMap.clear();
        linesMap.put("Total", new ArrayList<>());
        linesMap.put("Pass", new ArrayList<>());
        linesMap.put("Normal", new ArrayList<>());

        nextWeek = false;
        update();
    }

    /**
     * @param str   String die aangeeft welk type het gaat; Normal(reguliere), Pass(Pashouders) en Total
     * @param value de waarde, de hoeveelheid auto's
     *              Hier wordt berekend wat de x en y posities zijn van de volgende lijn
     *              Dit wordt elke tick aangeroepen vanuit de controller
     */
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


    /**
     * @param value Integer de hoeveelheid auto's
     *              Hier wordt de de y positie uitgerekend
     * @return double de y positie
     */
    private double getY(int value) {
        double _temp = 60.0 / 25.0;
        double bottomY = graphView.getHeight() - 25 - 10;
        double returnValue = (bottomY - (value / _temp));
        if (returnValue < 10) {
            return getY(540);
        }
        return (bottomY - (value / _temp));
    }

    /**
     * getX verkrijgt de x positie van de lijn ten opzichte van de tijd van de simulator(garage)
     * @return double, x positie van de volgende punten
     */
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


    /**
     * @param str type lijn
     * @param pX
     * @param pY
     * @param x
     * @param y
     */
    private void addLine(String str, double pX, double pY, double x, double y) {
        Line2D line = new Line2D.Double(pX, pY, x, y);
        ArrayList<Line2D> lines = linesMap.get(str);
        lines.add(line);
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
