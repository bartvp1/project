package MyComponents;

import Graph.GraphController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class CheckBox extends JComponent implements MouseListener {
    boolean on;
    double width;
    double height;
    boolean pressing = false;

    public CheckBox(boolean on) {
        this.on = on;
        setFocusable(true);
        requestFocus();
        addMouseListener(this);


    }

    public void setSize(double size) {
        this.width = size;
        this.height = size;
        repaint();
    }

    public void setSize(int size) {
        this.width = size;
        this.height = size;
        repaint();

    }


    public boolean isOn() {
        return on;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.draw(new Rectangle2D.Double(0, 0, width, height));
        g2.setColor(new Color(100, 100, 100));
        g2.fill(new Rectangle2D.Double(1, 1, width - 2, height - 2));


        if (on) {
            g2.setColor(Color.GREEN);
            g2.setStroke(new BasicStroke((int) width / 10));
            double tenthOfWidth = width / 10;
            g2.draw(new Line2D.Double(tenthOfWidth, height / 2, width / 3, tenthOfWidth * 9));
            g2.draw(new Line2D.Double(width / 3, tenthOfWidth * 9, tenthOfWidth * 9, tenthOfWidth));
        } else {
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke((int) width / 10));
            double tenthOfWidth = width / 10;

            g2.draw(new Line2D.Double(tenthOfWidth, tenthOfWidth, tenthOfWidth * 9, tenthOfWidth * 9));
            g2.draw(new Line2D.Double(tenthOfWidth, tenthOfWidth * 9, tenthOfWidth * 9, tenthOfWidth));

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        on = !on;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
