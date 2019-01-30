package QueuesSummary;

import MyComponents.Model;
import MyComponents.MyLabel;
import MyComponents.View;

import javax.swing.*;
import java.awt.*;

public class QueueSummaryView extends View {

    JLabel entranceQueueLabel = new MyLabel("", JLabel.CENTER, "description");
    JLabel entrancePassQueueLabel = new MyLabel("", JLabel.CENTER, "description");
    JLabel exitQueueLabel = new MyLabel("", JLabel.CENTER, "description");
    JLabel paymentQueueLabel = new MyLabel("", JLabel.CENTER, "description");


    public void init() {
        setBackground(new Color(47, 49, 54));
        MyLabel titleLabel = new MyLabel("Car Queues", JLabel.CENTER, "Title");
        titleLabel.setBounds(0, 0, getWidth(), 50);
        add(titleLabel);

        JPanel panel = new JPanel(new GridLayout(6,1,0,10));
        panel.setBounds(0, 50, getWidth(), getHeight()-50);
        panel.setOpaque(false);

        panel.add(entranceQueueLabel);
        panel.add(entrancePassQueueLabel);
        panel.add(exitQueueLabel);
        panel.add(paymentQueueLabel);



        add(panel);
    }

    @Override
    protected void update(Model model) {
        QueueSummaryModel qModel = (QueueSummaryModel) model;

        entranceQueueLabel.setText("Car Queue: " + qModel.getEntranceSize() + " car(s)");
        entrancePassQueueLabel.setText("Car (pass) Queue: " + qModel.getEntrancePassSize() + " car(s)");
        paymentQueueLabel.setText("Payment Queue: " + qModel.getPaymentSize() + " car(s)");
        exitQueueLabel.setText("Exit Queue: " + qModel.getExitQueue() + " car(s)");
    }
}
