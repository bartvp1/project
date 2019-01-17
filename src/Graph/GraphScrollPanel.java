package Graph;

import Garage.Garage;

import javax.swing.*;
import java.awt.*;

public class GraphScrollPanel extends JScrollPane {
    Garage garage;
    Dimension screenSize;
    Dimension size;

    //De breedte van de grafiek ten opzichte van de breedte van je scherm, in procenten.
    private int widthPercent = 90; // 90%
    GraphScrollContentPanel content;

    public GraphScrollPanel(Garage garage) {
        this.garage = garage;

    }

    public void init() {
        // de grootte van je scherm
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBorder(null);

        // Bereken de breedte van de grafiek
        Double widthOnePercent = (double) screenSize.width / 100.0;
        Double width = widthOnePercent * widthPercent;

        int height = getParent().getSize().height;
        this.size = new Dimension(width.intValue(), height);

        // Set size & location
        setPreferredSize(size);
        setSize(size);
        setLocation(widthOnePercent.intValue() * (100 - widthPercent), 5);

        getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        //content
        content = new GraphScrollContentPanel(garage);
        setViewportView(content);
        content.init();


        repaint();
    }

    public GraphScrollContentPanel getContent() {
        return this.content;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void setTickPause(int value) {
        content.setTicks(value);
    }

    public void toggleFillMode() {
        content.toggleFillMode();
    }

}
