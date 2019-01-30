package Finance;

import Garage.GarageController;
import Garage.GarageModel;
import MyComponents.MyLabel;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class FinanceView extends JPanel {
    JLabel regular = new MyLabel("", JLabel.CENTER, "description");
    JLabel reserved = new MyLabel("", JLabel.CENTER, "description");
    JLabel moneyCounter = new MyLabel("", JLabel.CENTER, "description");

    public void init() {
        JPanel finance = new JPanel(new BorderLayout());
        finance.setBounds(0, 0, getWidth(), getHeight());
        finance.setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setBackground(new Color(47, 49, 54));

        MyLabel titleLabel = new MyLabel("Finances", JLabel.CENTER, "Title");
        titleLabel.setBounds(0, 0, getWidth(), 50);
        finance.add(titleLabel,BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3,1,0,20));
        panel.setBounds(0, 50, getWidth(), getHeight()-100);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setOpaque(false);

        panel.add(regular);
        panel.add(reserved);
        panel.add(moneyCounter);

        finance.add(panel);
        super.add(finance);
    }

    public void update(GarageController garage) {

        DecimalFormat df = new DecimalFormat("0.00");

        regular.setText("Price (regular): € " + df.format(garage.getPriceRegular()));
        reserved.setText("Price (reservation): € " + df.format(garage.getPriceReservation()));
        moneyCounter.setText("Money earned: € " + df.format(garage.getMoneyEarned()));
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
