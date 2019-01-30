package Garage;

import Garage.Car.Car;
import MyComponents.Model;
import MyComponents.MyLabel;
import MyComponents.View;

import javax.swing.*;
import java.awt.*;

public class GarageView extends View {
    private GarageModel garageModel = null;
    private MyLabel title = new MyLabel("Simulator", JLabel.CENTER, "Title");

    public GarageView() {
        setBackground(new Color(47, 49, 54));
        repaint();
    }

    public void init() {
        title.setBounds(0, 0, getWidth(), 50);
        add(title);
    }

    @Override
    protected void update(Model model) {
        this.garageModel = (GarageModel) model;
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
                    color = new Color(255, 250, 150);
                }

                g2.setColor(color);

                int lotHeight = 10;
                int lotWidth = 20;
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
