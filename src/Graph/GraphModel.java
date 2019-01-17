package Graph;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphModel {
    private final static int STARTING_X = 10;
    int currentX;
    int a;
    HashMap<String, ArrayList<Line2D>> linesMap;


    public int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }


    public int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public void addLine(String str, int x, int y) {
        ArrayList<Line2D> lines = linesMap.get(str);
    }

    public ArrayList<Line2D> getLines(String str) {
        return linesMap.get(str);
    }

}
