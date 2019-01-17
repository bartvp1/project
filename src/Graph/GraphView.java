package Graph;

import javax.swing.*;
import java.awt.*;

public class GraphView extends JPanel {
    GraphModel model;

    public GraphView(GraphModel model) {
        super(null);
        this.model = model;
    }

    //    void drawGrid(Graphics2D g){
//        //Vertical Lines
//        int i = 0;
//        for (int x = beginX; x < currentSize.width; x += this.dayWidth) {
//            int count = (x - beginX) / gridSize;
//            if (x == beginX) {
//                g.setStroke(new BasicStroke(3));
//                g.setColor(new Color(0, 0, 0, 255));
//
//            } else {
//                g.setStroke(new BasicStroke(2));
//                g.setColor(new Color(0, 0, 0, 30));
//            }
//
//            g.drawLine(x, beginY, x, currentSize.height - gridSize - 5);
//            g.setColor(Color.WHITE);
//            g.drawString(dagen[i], x + (int) this.dayWidth / 2, currentSize.height - 10);
//            i++;
//        }
//
//        //Horizontal Lines
//        for (int y = 0; y < rows; y++) {
//            if (y == (rows - 1)) {
//                g.setStroke(new BasicStroke(3));
//                g.setColor(new Color(0, 0, 0, 255));
//            } else {
//                g.setStroke(new BasicStroke(2));
//                g.setColor(new Color(0, 0, 0, 30));
//            }
//            g.drawLine(beginX, beginY + (y * gridSize), currentSize.width, beginY + (y * gridSize));
//        }
//    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
