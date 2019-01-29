package Finance;

import MyComponents.MyLabel;

import javax.swing.*;
import java.awt.*;

public class FinanceView extends JPanel {
    public FinanceView() {
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setBackground(new Color(47, 49, 54));
        JLabel titleLabel = new MyLabel("Finances", JLabel.CENTER, "Title");
        titleLabel.setBounds(0, 0, getWidth(), 50);
        add(titleLabel);
        //add(new MyLabel("Price P/H: €" + FC.getPriceNormal(), JLabel.CENTER, "description"));
        //add(new MyLabel("Price Passholders P/H: €" + FC.getPricePassHolders(), JLabel.CENTER, "description"));
        //add(new MyLabel("Price Reservation P/H: €" + FC.getPriceReservation(), JLabel.CENTER, "description"));
        //add(new MyLabel("Total provit €" + FC.getTotalEarned(), JLabel.CENTER, "description"));

    }
}
