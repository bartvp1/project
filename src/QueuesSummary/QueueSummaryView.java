package QueuesSummary;

import Garage.GarageModel;
import MyComponents.Model;
import MyComponents.MyLabel;
import MyComponents.View;

import javax.swing.*;
import java.awt.*;

public class QueueSummaryView extends View {

    private JLabel entranceQueueLabel;
    private JLabel entrancePassQueueLabel;
    private JLabel exitQueueLabel;
    private JLabel paymentQueueLabel;

    public void init() {
        setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        setBackground(new Color(47, 49, 54));

        JPanel panel = new JPanel(new GridLayout(10, 1, 0, 0));
        MyLabel titleLabel = new MyLabel("Car Queues", JLabel.CENTER, "Title");
        titleLabel.setBounds(0, 0, getWidth(), 50);
        panel.add(titleLabel);

        entranceQueueLabel = new MyLabel("xx", JLabel.CENTER,"description");
        entrancePassQueueLabel = new MyLabel("xx", JLabel.CENTER,"description");
        exitQueueLabel = new MyLabel("xx", JLabel.CENTER,"description");
        paymentQueueLabel = new MyLabel("xx", JLabel.CENTER,"description");

        panel.add(entranceQueueLabel);
        panel.add(entrancePassQueueLabel);
        panel.add(exitQueueLabel);
        panel.add(paymentQueueLabel);

        repaint();
    }

    @Override
    protected void update(Model model) {
        QueueSummaryModel qModel = (QueueSummaryModel) model;

        entranceQueueLabel.setText("Car Queue: " + qModel.getEntranceSize());
        entrancePassQueueLabel.setText("Car (pass) Queue: " + qModel.getEntrancePassSize() );
        paymentQueueLabel.setText("Payment Queue: " + qModel.getPaymentSize());
        exitQueueLabel.setText("Exit Queue: " + qModel.getExitQueue());

        repaint();
    }
}
