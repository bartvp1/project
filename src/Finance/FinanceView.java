package Finance;

import Garage.GarageController;
import Garage.GarageModel;
import MyComponents.MyLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class FinanceView extends JPanel {
    
    GarageController garageController;
    
    JLabel regular = new MyLabel("", JLabel.CENTER, "description");
    JLabel reserved = new MyLabel("", JLabel.CENTER, "description");
    JLabel moneyCounter = new MyLabel("", JLabel.CENTER, "description");
    JLabel totalCarsCount = new MyLabel("", JLabel.CENTER, "description");

    JLabel monday = new MyLabel("", JLabel.CENTER, "description");
    JLabel tuesday = new MyLabel("", JLabel.CENTER, "description");
    JLabel wednesday = new MyLabel("", JLabel.CENTER, "description");
    JLabel thursday = new MyLabel("", JLabel.CENTER, "description");
    JLabel friday = new MyLabel("", JLabel.CENTER, "description");
    JLabel saturdag = new MyLabel("", JLabel.CENTER, "description");
    JLabel sunday = new MyLabel("", JLabel.CENTER, "description");
    JButton restetBtn = new JButton("RESET FINANCES");

    public void init() {
        JPanel finance = new JPanel(new BorderLayout());
        finance.setBounds(0, 0, getWidth(), getHeight());
        finance.setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setBackground(new Color(47, 49, 54));

        MyLabel titleLabel = new MyLabel("Finances", JLabel.CENTER, "Title");
        titleLabel.setBounds(0, 0, getWidth(), 50);
        finance.add(titleLabel,BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(15,1,0,10));
        panel.setBounds(0, 50, getWidth(), getHeight()-100);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setOpaque(false);

        panel.add(moneyCounter);
        panel.add(totalCarsCount);
        panel.add(monday);
        panel.add(tuesday);
        panel.add(wednesday);
        panel.add(thursday);
        panel.add(friday);
        panel.add(saturdag);
        panel.add(sunday);
        panel.add(restetBtn);

        restetBtn.addActionListener(e -> reset());



        finance.add(panel);
        super.add(finance);
    }

    void reset()
    {
        if(garageController != null)
        garageController.resetMoney();
    }


    public void update(GarageController garage) {
        garageController = garage;
        DecimalFormat df = new DecimalFormat("0.00");

        //regular.setText("Price (regular): € " + df.format(garage.getPriceRegular()));
        //reserved.setText("Price (reservation): € " + df.format(garage.getPriceReservation()));
        moneyCounter.setText("TOTAL Money earned: € " + df.format(garage.getMoneyEarned()));
        totalCarsCount.setText("Total cars payed: " + garage.gettotalCarsPayed());
        monday.setText("Revenue Monday: €" + df.format(garage.getMoneyADay(0)));
        tuesday.setText("Revenue Tuesday: €" + df.format(garage.getMoneyADay(1)));
        wednesday.setText("Revenue Wednesday: €" + df.format(garage.getMoneyADay(2)));
        thursday.setText("Revenue Thursday: €" + df.format(garage.getMoneyADay(3)));
        friday.setText("Revenue Friday: €" + df.format(garage.getMoneyADay(4)));
        saturdag.setText("Revenue Saturdag: €" + df.format(garage.getMoneyADay(5)));
        sunday.setText("Revenue Sunday: €" + df.format(garage.getMoneyADay(6)));
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

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
