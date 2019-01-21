package Garage;

import MyComponents.MyLabel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GarageView extends JPanel {
    GarageModel garageModel = null;
    private int lotWidth = 20;
    private int lotHeight = 10;
    MyLabel title = new MyLabel("Garage", JLabel.CENTER, "Title");

    public GarageView() {

        title.setBounds(0, 0, getWidth(), getHeight());
        add(title);


        setBackground(new Color(47, 49, 54));
        LineBorder border = new LineBorder(Color.BLACK);

        repaint();

    }


    public void update(GarageModel garageModel) {
        this.garageModel = garageModel;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (garageModel != null) {
            int size = garageModel.getLocations().size();
            for (int i = 0; i < size; i++) {
                Location loc = garageModel.getLocations().get(i);
                Car car = garageModel.getCarAt(loc);
                Color color = car != null ? car.getColor() : i < garageModel.getReservedLocationsPass() ? new Color(100, 180, 250) : new Color(255, 180, 180);
                if (garageModel.getReservedLocations().contains(loc)) {
                    color = new Color(170, 255, 170);
                }

                g2.setColor(color);

                g2.fillRect(
                        garageModel.getLocations().get(i).getFloor() * 280 + (1 + (int) Math.floor(garageModel.getLocations().get(i).getRow() * 0.5)) * 75 + (garageModel.getLocations().get(i).getRow() % 2) * 20,
                        title.getHeight() + 10 + garageModel.getLocations().get(i).getPlace() * lotHeight,
                        lotWidth - 1,
                        lotHeight - 1);
            }
        }


        g2.setColor(new Color(0, 0, 0, 150));
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(getWidth() - 2, 0, getWidth() - 2, getHeight() - 4);
        g2.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);


        g.setColor(new Color(0, 0, 0, 150));
        g2.setStroke(new BasicStroke(.4f));
        g.drawLine(0, 0, getWidth() - 1, 0);
        g.drawLine(0, 0, 0, getHeight());


    }
}
