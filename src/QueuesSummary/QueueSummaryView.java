package QueuesSummary;

import MyComponents.Model;
import MyComponents.MyLabel;
import MyComponents.View;

import javax.swing.*;
import java.awt.*;

public class QueueSummaryView extends View {

    JLabel entranceQueueLabel = new JLabel();
    JLabel entrancePassQueueLabel = new JLabel();
    JLabel exitQueueLabel = new JLabel();
    JLabel paymentQueueLabel = new JLabel();


    public void init() {
        setBackground(new Color(47, 49, 54));
        MyLabel titleLabel = new MyLabel("Car Queues", JLabel.CENTER, "Title");
        titleLabel.setBounds(0, 0, getWidth(), 50);
        add(titleLabel);

        JPanel panel = new JPanel(new GridLayout(6,1,0,10));
        panel.setBounds(0, 50, getWidth(), getHeight()-50);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
        panel.setOpaque(false);

        panel.add(entranceQueueLabel);
        panel.add(entrancePassQueueLabel);
        panel.add(exitQueueLabel);
        panel.add(paymentQueueLabel);

        entranceQueueLabel.setForeground(Color.WHITE);
        entrancePassQueueLabel.setForeground(Color.WHITE);
        exitQueueLabel.setForeground(Color.WHITE);
        paymentQueueLabel.setForeground(Color.WHITE);

        Font font = new Font("Dubai Light", Font.PLAIN, 18);

        entranceQueueLabel.setFont(font);
        entrancePassQueueLabel.setFont(font);
        exitQueueLabel.setFont(font);
        paymentQueueLabel.setFont(font);

        add(panel);
    }

    @Override
    protected void update(Model model) {
        QueueSummaryModel qModel = (QueueSummaryModel) model;

        entranceQueueLabel.setText("Car Queue: " + qModel.getEntranceSize());
        entrancePassQueueLabel.setText("Car (pass) Queue: " + qModel.getEntrancePassSize() );
        paymentQueueLabel.setText("Payment Queue: " + qModel.getPaymentSize());
        exitQueueLabel.setText("Exit Queue: " + qModel.getExitQueue());
    }
}
